package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.CurrentMarketPrice;
import com.nam20.news_invest.entity.DailyStockMetric;
import com.nam20.news_invest.enums.AssetType;
import com.nam20.news_invest.event.PriceSpikeEvent;
import com.nam20.news_invest.repository.CurrentMarketPriceRepository;
import com.nam20.news_invest.repository.DailyStockMetricRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AlphaVantageStockService {

    private final WebClient webClient;
    private final DailyStockMetricRepository dailyStockMetricRepository;
    private final CurrentMarketPriceRepository currentMarketPriceRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${ALPHA_VANTAGE_API_KEY}")
    private String apiKey;

    private final List<String> symbols = List.of("VOO","QQQ");
    private static final double SPIKE_THRESHOLD = 0.05; // 5%

    public AlphaVantageStockService(WebClient.Builder webClientBuilder,
                                    DailyStockMetricRepository dailyStockMetricRepository,
                                    CurrentMarketPriceRepository currentMarketPriceRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.dailyStockMetricRepository = dailyStockMetricRepository;
        this.currentMarketPriceRepository = currentMarketPriceRepository;
        this.eventPublisher = eventPublisher;
    }

    @CacheEvict(value = "dailyStockPrices", allEntries = true)
    public void processAllSymbols() {
        symbols.forEach(this::getAndSaveStockData);
    }

    private void getAndSaveStockData(String symbol) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "TIME_SERIES_DAILY")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", "5min")
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(jsonNode -> {
                    JsonNode timeSeries = jsonNode.get("Time Series (Daily)");
                    return Flux.fromIterable(timeSeries::fields);
                })
                .map(entry -> convertToDailyStockPrice(entry, symbol))
                .collectList()
                .doOnNext(dailyStockPrices -> {
                    dailyStockMetricRepository.saveAll(dailyStockPrices);
                    updateCurrentMarketPrice(symbol);
                })
                .subscribe();
    }

    private DailyStockMetric convertToDailyStockPrice(Map.Entry<String, JsonNode> stockData, String symbol) {
        String date = stockData.getKey();
        JsonNode dailyData = stockData.getValue();

        return DailyStockMetric.builder()
                .symbol(symbol)
                .date(LocalDate.parse(date))
                .openPrice(dailyData.get("1. open").asDouble())
                .highPrice(dailyData.get("2. high").asDouble())
                .lowPrice(dailyData.get("3. low").asDouble())
                .closePrice(dailyData.get("4. close").asDouble())
                .volume(dailyData.get("5. volume").asLong())
                .build();
    }

    private void updateCurrentMarketPrice(String symbol) {
        Optional<DailyStockMetric> optionalLatestPrice =
                dailyStockMetricRepository.findFirstBySymbolOrderByDateDesc(symbol);

        if (optionalLatestPrice.isEmpty()) {
            return;
        }

        DailyStockMetric latestPrice = optionalLatestPrice.get();
        BigDecimal newPrice = BigDecimal.valueOf(latestPrice.getClosePrice());

        currentMarketPriceRepository.findByTypeAndSymbol(AssetType.STOCK, symbol).ifPresentOrElse(
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
                                    symbol,
                                    AssetType.STOCK,
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
                            .type(AssetType.STOCK)
                            .symbol(symbol)
                            .price(newPrice.doubleValue())
                            .build());
                }
        );
    }
}
