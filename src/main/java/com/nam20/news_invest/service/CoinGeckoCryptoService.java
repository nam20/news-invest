package com.nam20.news_invest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CoinGeckoCryptoService {

    private final WebClient webClient;

    @Value("${COIN_GECKO_API_KEY}")
    private String apiKey;

    private List<String> coinNames = List.of("bitcoin");

    public CoinGeckoCryptoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.coingecko.com").build();
    }

    public Mono<String> getCryptoData(String coinName) {
        String path = """
                /api/v3/coins/%s/market_chart
                """.formatted(coinName);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("x_cg_api_key", apiKey)
                        .queryParam("vs_currency", "krw")
                        .queryParam("days", 1)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
