package com.nam20.news_invest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FredEconomicDataService {

    private final WebClient webClient;

    @Value("${FRED_API_KEY}")
    private String apiKey;

    public FredEconomicDataService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.stlouisfed.org").build();
    }

    public Mono<String> getEconomicData() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fred/series/observations")
                        .queryParam("series_id", "GNPCA")
                        .queryParam("api_key", apiKey)
                        .queryParam("file_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
