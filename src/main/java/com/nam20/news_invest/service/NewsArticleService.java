package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.mapper.NewsArticleMapper;
import com.nam20.news_invest.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NewsArticleMapper newsArticleMapper;

    public List<NewsArticleResponse> retrieveNewsArticles() {
        return newsArticleRepository.findAll()
                .stream()
                .map(newsArticleMapper::toDto)
                .toList();
    }
}
