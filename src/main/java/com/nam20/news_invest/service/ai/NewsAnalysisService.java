package com.nam20.news_invest.service.ai;

import com.nam20.news_invest.dto.ai.NewsAnalysisResponse;
import com.nam20.news_invest.entity.NewsArticle;
import com.nam20.news_invest.entity.ai.NewsAnalysis;
import com.nam20.news_invest.mapper.ai.NewsAnalysisMapper;
import com.nam20.news_invest.repository.ai.NewsAnalysisRepository;
import com.nam20.news_invest.service.PromptTemplateService;
import com.nam20.news_invest.util.GeminiApiClient;
import com.nam20.news_invest.util.GeminiResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NewsAnalysisService {
    private final PromptTemplateService promptTemplateService;
    private final GeminiApiClient geminiApiClient;
    private final GeminiResponseParser geminiResponseParser;
    private final NewsAnalysisMapper newsAnalysisMapper;
    private final NewsAnalysisRepository newsAnalysisRepository;

    /**
     * 뉴스 분석 프롬프트 생성, Gemini 호출, 파싱, 엔티티 변환 및 저장
     * @param variables 프롬프트 변수 맵
     * @param newsArticle 연관 뉴스 기사 엔티티
     * @return 저장된 NewsAnalysis 엔티티
     */
    @Transactional
    public NewsAnalysis analyzeAndSave(Map<String, String> variables, NewsArticle newsArticle) throws Exception {
        String prompt = promptTemplateService.createNewsAnalysisPrompt(variables);

        String response = geminiApiClient.generateContent(prompt);

        NewsAnalysisResponse parsed = geminiResponseParser.parseNewsAnalysis(response);

        NewsAnalysis entity = newsAnalysisMapper.toEntity(parsed, newsArticle);
        
        return newsAnalysisRepository.save(entity);
    }
} 