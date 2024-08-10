package com.nam20.news_invest.controller;

import com.nam20.news_invest.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiClientController {

    private final NewsService newsService;

    @GetMapping("/insert-news")
    public void retrieveNews() {
        newsService.insertNews();
    }
}
