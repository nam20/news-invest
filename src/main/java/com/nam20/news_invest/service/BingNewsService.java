package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.NewsArticle;
import com.nam20.news_invest.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class BingNewsService {

    private final WebClient webClient;
    private final NewsArticleRepository newsArticleRepository;

    @Value("${BIND_NEWS_SEARCH_API_KEY}")
    private String apiKey;

    private List<String> searchQueries = List.of("stock");

    public BingNewsService(WebClient.Builder webClientBuilder,
                           NewsArticleRepository newsArticleRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.bing.microsoft.com").build();
        this.newsArticleRepository = newsArticleRepository;
    }

    public void processAllSearchQueries() {
        searchQueries.forEach(this::getAndSaveNews);
    }

    private void getAndSaveNews(String searchQuery) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v7.0/news/search")
                        .queryParam("q", searchQuery)
                        .queryParam("mkt", "en-US")
                        .queryParam("freshness", "Day")
                        .build())
                .header("Ocp-Apim-Subscription-Key", apiKey)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(jsonNode -> Flux.fromIterable(jsonNode.get("value")))
                .map(this::convertToNewsArticle)
                .subscribe(newsArticleRepository::save);
    }

    private NewsArticle convertToNewsArticle(JsonNode article) {
        Instant instant = Instant.parse(article.get("datePublished").asText());
        LocalDateTime publishedAt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        return NewsArticle.builder()
                .title(article.get("name").asText())
                .url(article.get("url").asText())
                .description(article.get("description").asText())
                .thumbnailUrl(article.get("image").get("thumbnail").get("contentUrl").asText())
                .provider(article.get("provider").get(0).get("name").asText())
                .publishedAt(publishedAt)
                .build();
    }
}
