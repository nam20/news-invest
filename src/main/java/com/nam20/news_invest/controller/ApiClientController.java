package com.nam20.news_invest.controller;

import com.nam20.news_invest.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiClientController {

    private NewsService newsService;

    public ApiClientController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/insert-news")
    public void retrieveNews() {
        newsService.insertNews();
    }
}
