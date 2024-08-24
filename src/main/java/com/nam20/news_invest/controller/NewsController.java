package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsArticleService newsArticleService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<NewsArticleResponse>> retrieveNewsArticles(
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(newsArticleService.retrieveNewsArticles(page, PAGE_SIZE));
    }
}
