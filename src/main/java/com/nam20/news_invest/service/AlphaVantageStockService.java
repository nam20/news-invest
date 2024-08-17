package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.DailyStockPrice;
import com.nam20.news_invest.repository.DailyStockPriceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AlphaVantageStockService {

    private final WebClient webClient;
    private final DailyStockPriceRepository dailyStockPriceRepository;

    @Value("${ALPHA_VANTAGE_API_KEY}")
    private String apiKey;

    private final List<String> symbols = List.of("VOO","QQQ");

    public AlphaVantageStockService(WebClient.Builder webClientBuilder,
                                    DailyStockPriceRepository dailyStockPriceRepository) {
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.dailyStockPriceRepository = dailyStockPriceRepository;
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
                .subscribe(dailyStockPriceRepository::save);
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
}
