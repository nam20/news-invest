package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.NewsApiResponseArticle;
import com.nam20.news_invest.entity.News;
import com.nam20.news_invest.mapper.NewsMapper;
import com.nam20.news_invest.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final ApiClientService apiClientService;
    private final NewsMapper newsMapper;

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
