package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.nam20.news_invest.entity.ai.NewsAnalysis;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsArticleService newsArticleService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<NewsArticleResponse>> retrieveNewsArticles(
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(newsArticleService.retrieveNewsArticles(page, PAGE_SIZE));
    }

    /**
     * 뉴스 기사 AI 분석 요청 API
     * @param newsArticleId 분석할 뉴스 기사 ID
     * @return 생성된 NewsAnalysis 결과
     */
    @PostMapping("/{newsArticleId}/analyze")
    public ResponseEntity<NewsAnalysis> analyzeNewsArticle(
            @PathVariable Long newsArticleId
    ) {
        // 빈 변수 맵으로 분석 실행
        NewsAnalysis result = newsArticleService.analyzeNewsArticle(newsArticleId);
        return ResponseEntity.ok(result);
    }
}
