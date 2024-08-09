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
    private final NewsMapper newsMapper;

    public NewsService(NewsRepository newsRepository, ApiClientService apiClientService,
                       NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.apiClientService = apiClientService;
        this.newsMapper = newsMapper;
    }

    public void insertNews() {
        List<NewsApiResponseArticle> articles = apiClientService.retrieveNews().getArticles();

        for (NewsApiResponseArticle article : articles) {
            News news = newsMapper.toEntity(article);
            newsRepository.save(news);
        }
    }

    public List<News> retrieveNews() {
        return newsRepository.findAll();
    }
}
