package com.nam20.news_invest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AlphaVantageStockService {

    private final WebClient webClient;

    @Value("${ALPHA_VANTAGE_API_KEY}")
    private String apiKey;

    private final List<String> symbols = List.of("VOO","QQQ");

    public AlphaVantageStockService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
    }

    public Mono<String> getStockData(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "TIME_SERIES_DAILY")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", "5min")
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
