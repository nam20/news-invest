package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.CurrentMarketPrice;
import com.nam20.news_invest.entity.DailyStockPrice;
import com.nam20.news_invest.repository.CurrentMarketPriceRepository;
import com.nam20.news_invest.repository.DailyStockPriceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AlphaVantageStockService {

    private final WebClient webClient;
    private final DailyStockPriceRepository dailyStockPriceRepository;
    private final CurrentMarketPriceRepository currentMarketPriceRepository;

    @Value("${ALPHA_VANTAGE_API_KEY}")
    private String apiKey;

    private final List<String> symbols = List.of("VOO","QQQ");

    public AlphaVantageStockService(WebClient.Builder webClientBuilder,
                                    DailyStockPriceRepository dailyStockPriceRepository,
                                    CurrentMarketPriceRepository currentMarketPriceRepository) {
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.dailyStockPriceRepository = dailyStockPriceRepository;
        this.currentMarketPriceRepository = currentMarketPriceRepository;
    }

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
                    dailyStockPriceRepository.saveAll(dailyStockPrices);
                    updateCurrentMarketPrice(symbol);
                })
                .subscribe();
    }

    private DailyStockPrice convertToDailyStockPrice(Map.Entry<String, JsonNode> stockData, String symbol) {
        String date = stockData.getKey();
        JsonNode dailyData = stockData.getValue();

        return DailyStockPrice.builder()
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
        Optional<DailyStockPrice> optionalLatestPrice =
                dailyStockPriceRepository.findFirstBySymbolOrderByDateDesc(symbol);

        if (optionalLatestPrice.isEmpty()) {
            return;
        }

        DailyStockPrice latestPrice = optionalLatestPrice.get();
        Double closePrice = latestPrice.getClosePrice();

        currentMarketPriceRepository.findByTypeAndSymbol("stock", symbol).ifPresentOrElse(
                currentMarketPrice -> {
                    currentMarketPrice.updatePrice(closePrice);
                    currentMarketPriceRepository.save(currentMarketPrice);
                },
                () -> {
                    currentMarketPriceRepository.save(CurrentMarketPrice.builder()
                            .type("stock")
                            .symbol(symbol)
                            .price(closePrice)
                            .build());
                }
        );
    }
}
