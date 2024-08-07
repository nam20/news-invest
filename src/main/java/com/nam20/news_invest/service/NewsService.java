package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.NewsApiResponseArticle;
import com.nam20.news_invest.entity.News;
import com.nam20.news_invest.mapper.NewsMapper;
import com.nam20.news_invest.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final ApiClientService apiClientService;

    public NewsService(NewsRepository newsRepository, ApiClientService apiClientService) {
        this.newsRepository = newsRepository;
        this.apiClientService = apiClientService;
    }

    public void insertNews() {
        List<NewsApiResponseArticle> articles = apiClientService.retrieveNews().getArticles();

        for (NewsApiResponseArticle article : articles) {
            News news = NewsMapper.toEntity(article);
            newsRepository.save(news);
        }
    }
}
