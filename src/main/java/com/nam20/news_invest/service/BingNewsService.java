package com.nam20.news_invest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BingNewsService {

    private final WebClient webClient;

    @Value("${BIND_NEWS_SEARCH_API_KEY}")
    private String apiKey;

    public BingNewsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.bing.microsoft.com").build();
    }

    public Mono<String> getNews() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v7.0/news/search")
                        .queryParam("q", "stock market")
                        .queryParam("count", 10)
                        .queryParam("mkt", "en-US")
                        .build())
                .header("Ocp-Apim-Subscription-Key", apiKey)
                .retrieve()
                .bodyToMono(String.class);
    }
}