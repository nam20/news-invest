package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.NewsArticle;
import com.nam20.news_invest.mapper.NewsArticleMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.NewsArticleRepository;
import com.nam20.news_invest.service.ai.NewsAnalysisService;
import com.nam20.news_invest.entity.ai.NewsAnalysis;
import com.nam20.news_invest.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NewsArticleMapper newsArticleMapper;
    private final PaginationMetaMapper paginationMetaMapper;
    private final NewsAnalysisService newsAnalysisService;

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

    /**
     * 주어진 뉴스 기사 ID로 AI 분석 결과를 생성 및 저장
     * @param newsArticleId 분석할 뉴스 기사 ID
     * @return 생성된 NewsAnalysis 엔티티
     */
    public NewsAnalysis analyzeNewsArticle(Long newsArticleId) {
        NewsArticle newsArticle = newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 뉴스 기사가 존재하지 않습니다: " + newsArticleId));
        
        // TODO: variables에 값 채워넣기
        Map<String, String> variables = new HashMap<>(); 
        
        return newsAnalysisService.analyzeAndSave(variables, newsArticle);
    }
}
