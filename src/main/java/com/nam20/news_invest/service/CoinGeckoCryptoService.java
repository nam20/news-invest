package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.CurrentMarketPrice;
import com.nam20.news_invest.entity.DailyCoinMetric;
import com.nam20.news_invest.enums.AssetType;
import com.nam20.news_invest.event.PriceSpikeEvent;
import com.nam20.news_invest.repository.CurrentMarketPriceRepository;
import com.nam20.news_invest.repository.DailyCoinMetricRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CoinGeckoCryptoService {

    private final WebClient webClient;
    private final DailyCoinMetricRepository dailyCoinMetricRepository;
    private final CurrentMarketPriceRepository currentMarketPriceRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${COIN_GECKO_API_KEY}")
    private String apiKey;

    private final List<String> coinNames = List.of("bitcoin");
    private static final double SPIKE_THRESHOLD = 0.05; // 5%

    public CoinGeckoCryptoService(WebClient.Builder webClientBuilder,
                                  DailyCoinMetricRepository dailyCoinMetricRepository,
                                  CurrentMarketPriceRepository currentMarketPriceRepository,
                                  ApplicationEventPublisher eventPublisher) {
        this.webClient = webClientBuilder.baseUrl("https://api.coingecko.com").build();
        this.dailyCoinMetricRepository = dailyCoinMetricRepository;
        this.currentMarketPriceRepository = currentMarketPriceRepository;
        this.eventPublisher = eventPublisher;
    }

    @CacheEvict(value = "dailyCoinMarketData", allEntries = true)
    public void processAllCoins() {
        coinNames.forEach(this::getAndSaveCryptoData);
    }

    private void getAndSaveCryptoData(String coinName) {
        String path = """
                /api/v3/coins/%s/market_chart
                """.formatted(coinName).trim();

        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("x_cg_api_key", apiKey)
                        .queryParam("vs_currency", "usd")
                        .queryParam("days", 1)
                        .queryParam("interval", "daily")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(jsonNode -> {
                    List<HashMap<String, Object>> marketChartList = new ArrayList<>();

                    for (int i = 0; i < jsonNode.get("prices").size() - 1; i++) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("timestamp", jsonNode.get("prices").get(i).get(0).asLong());
                        map.put("price", jsonNode.get("prices").get(i).get(1).asDouble());
                        map.put("market_cap", jsonNode.get("market_caps").get(i).get(1).asDouble());
                        map.put("total_volume", jsonNode.get("total_volumes").get(i).get(1).asDouble());
                        marketChartList.add(map);
                    }

                    return Flux.fromIterable(marketChartList);
                })
                .map(entry -> convertToDailyCoinMarketData(entry, coinName))
                .collectList()
                .doOnNext(dailyCoinMarketData -> {
                    dailyCoinMetricRepository.saveAll(dailyCoinMarketData);
                    updateCurrentMarketPrice(coinName);
                })
                .subscribe();
    }

    private DailyCoinMetric convertToDailyCoinMarketData(HashMap<String, Object> marketChart, String coinName) {
        LocalDate date = Instant.ofEpochMilli((Long) marketChart.get("timestamp"))
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return DailyCoinMetric.builder()
                .name(coinName)
                .date(date)
                .price((Double) marketChart.get("price"))
                .marketCap((Double) marketChart.get("market_cap"))
                .volume((Double) marketChart.get("total_volume"))
                .build();
    }

    private void updateCurrentMarketPrice(String name) {
        Optional<DailyCoinMetric> optionalLatestPrice =
                dailyCoinMetricRepository.findFirstByNameOrderByDateDesc(name);

        if (optionalLatestPrice.isEmpty()) {
            return;
        }

        DailyCoinMetric latestPrice = optionalLatestPrice.get();
        BigDecimal newPrice = BigDecimal.valueOf(latestPrice.getPrice());

        currentMarketPriceRepository.findByTypeAndSymbol(AssetType.COIN, name).ifPresentOrElse(
                currentMarketPrice -> {
                    BigDecimal oldPrice = BigDecimal.valueOf(currentMarketPrice.getPrice());
                    currentMarketPrice.updatePrice(newPrice.doubleValue());
                    currentMarketPriceRepository.save(currentMarketPrice);

                    // 가격 급등락 이벤트 감지 및 발행
                    if (oldPrice.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal change = newPrice.subtract(oldPrice);
                        BigDecimal percentageChangeDecimal = change.divide(oldPrice, 4, RoundingMode.HALF_UP);
                        double percentageChange = percentageChangeDecimal.doubleValue();

                        if (Math.abs(percentageChange) > SPIKE_THRESHOLD) {
                            PriceSpikeEvent event = new PriceSpikeEvent(
                                    this,
                                    name,
                                    AssetType.COIN,
                                    oldPrice,
                                    newPrice,
                                    percentageChange * 100
                            );
                            eventPublisher.publishEvent(event);
                        }
                    }
                },
                () -> {
                    currentMarketPriceRepository.save(CurrentMarketPrice.builder()
                            .type(AssetType.COIN)
                            .symbol(name)
                            .price(newPrice.doubleValue())
                            .build());
                }
        );
    }
}
