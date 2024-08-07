package com.nam20.news_invest.controller;

import com.nam20.news_invest.entity.News;
import com.nam20.news_invest.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public List<News> retrieveNews() {
        return newsService.retrieveNews();
    }
}
