package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.NewsArticle;
import com.nam20.news_invest.mapper.NewsArticleMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NewsArticleMapper newsArticleMapper;
    private final PaginationMetaMapper paginationMetaMapper;

    @Cacheable(value = "newsArticles", key = "#page + '-' + #size")
    public PaginationResponse<NewsArticleResponse> retrieveNewsArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<NewsArticle> newsArticlesPage = newsArticleRepository.findAll(pageable);

        List<NewsArticleResponse> newsArticleResponses = newsArticlesPage
                .getContent()
                .stream()
                .map(newsArticleMapper::toDto)
                .toList();

        return new PaginationResponse<>(newsArticleResponses,
                paginationMetaMapper.toPaginationMeta(newsArticlesPage));
    }
}
