package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DeeplTranslateService {

    private final WebClient webClient;

    @Value("${DEEPL_API_KEY}")
    private String apiKey;

    public DeeplTranslateService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api-free.deepl.com").build();
    }

    public Mono<List<String>> translateTexts(List<String> texts) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/translate")
                        .build())
                .header("Content-Type", "application/json")
                .header("Authorization", "DeepL-Auth-Key " + apiKey)
                .bodyValue(Map.of(
                        "text", texts,
                        "source_lang", "EN",
                        "target_lang", "KO"
                ))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> {
                    List<String> translations = new ArrayList<>();
                    jsonNode.get("translations").forEach(translation -> {
                        translations.add(translation.get("text").asText());
                    });
                    return translations;
                });
    }
}
