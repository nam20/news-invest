package com.nam20.news_invest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CoinGeckoCryptoService {

    private final WebClient webClient;

    @Value("${COIN_GECKO_API_KEY}")
    private String apiKey;

    public CoinGeckoCryptoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.coingecko.com").build();
    }

    public Mono<String> getCryptoData() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/coins/bitcoin")
                        .queryParam("x_cg_api_key", apiKey)
                        .queryParam("vs_currency", "usd")
                        .queryParam("days", 1)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
