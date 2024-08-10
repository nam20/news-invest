package com.nam20.news_invest.controller;

import com.nam20.news_invest.entity.News;
import com.nam20.news_invest.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/news")
    public List<News> retrieveNews() {
        return newsService.retrieveNews();
    }
}
