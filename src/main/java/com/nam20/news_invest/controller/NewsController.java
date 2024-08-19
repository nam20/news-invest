package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsArticleService newsArticleService;

    @GetMapping
    public ResponseEntity<List<NewsArticleResponse>> retrieveNewsArticles() {
        return ResponseEntity.ok(newsArticleService.retrieveNewsArticles());
    }
}
