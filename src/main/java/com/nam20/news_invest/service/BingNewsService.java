package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.NewsArticle;
import com.nam20.news_invest.event.NewsPublishedEvent;
import com.nam20.news_invest.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import org.springframework.cache.annotation.CacheEvict;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class BingNewsService {

    private final WebClient webClient;
    private final NewsArticleRepository newsArticleRepository;
    private final DeeplTranslateService deeplTranslateService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${BING_NEWS_SEARCH_API_KEY}")
    private String apiKey;

    private final List<String> searchQueries = List.of("stock");

    public BingNewsService(WebClient.Builder webClientBuilder,
                           NewsArticleRepository newsArticleRepository,
                           DeeplTranslateService deeplTranslateService,
                           ApplicationEventPublisher eventPublisher) {
        this.webClient = webClientBuilder.baseUrl("https://api.bing.microsoft.com").build();
        this.newsArticleRepository = newsArticleRepository;
        this.deeplTranslateService = deeplTranslateService;
        this.eventPublisher = eventPublisher;
    }

    @CacheEvict(value = "newsArticles", allEntries = true)
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
                .flatMapMany(jsonNode -> {

                    List<String> textsToTranslate = new ArrayList<>();
                    JsonNode responseArticles = jsonNode.get("value");
                    responseArticles.forEach(article -> {
                        textsToTranslate.add(article.get("name").asText());
                        textsToTranslate.add(article.get("description").asText());
                    });

                    return deeplTranslateService.translateTexts(textsToTranslate)
                            .flatMapMany(translations -> {
                                List<NewsArticle> articles = new ArrayList<>();
                                for (int i = 0; i < translations.size(); i += 2) {
                                    String translatedTitle = translations.get(i);
                                    String translatedDescription = translations.get(i + 1);
                                    JsonNode articleJson = responseArticles.get(i / 2);

                                    NewsArticle article = convertToNewsArticle(
                                            articleJson, translatedTitle, translatedDescription);
                                    articles.add(article);
                                }
                                return Flux.fromIterable(articles);
                            });
                })
                .collectList()
                .doOnNext(newsArticles -> {
                    newsArticleRepository.saveAll(newsArticles);
                    newsArticles.forEach(article -> eventPublisher.publishEvent(new NewsPublishedEvent(this, article)));
                })
                .subscribe();
    }

    private NewsArticle convertToNewsArticle(JsonNode article, String title, String description) {
        Instant instant = Instant.parse(article.get("datePublished").asText());
        LocalDateTime publishedAt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        return NewsArticle.builder()
                .title(title)
                .url(article.get("url").asText())
                .description(description)
                .thumbnailUrl(article.get("image").get("thumbnail").get("contentUrl").asText())
                .provider(article.get("provider").get(0).get("name").asText())
                .publishedAt(publishedAt)
                .build();
    }
}
