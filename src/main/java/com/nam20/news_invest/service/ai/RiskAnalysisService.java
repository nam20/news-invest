package com.nam20.news_invest.service.ai;

import com.nam20.news_invest.dto.ai.RiskAnalysisResponse;
import com.nam20.news_invest.entity.Portfolio;
import com.nam20.news_invest.entity.ai.RiskAnalysis;
import com.nam20.news_invest.mapper.ai.RiskAnalysisMapper;
import com.nam20.news_invest.repository.ai.RiskAnalysisRepository;
import com.nam20.news_invest.service.PromptTemplateService;
import com.nam20.news_invest.util.GeminiApiClient;
import com.nam20.news_invest.util.GeminiResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RiskAnalysisService {
    private final PromptTemplateService promptTemplateService;
    private final GeminiApiClient geminiApiClient;
    private final GeminiResponseParser geminiResponseParser;
    private final RiskAnalysisMapper riskAnalysisMapper;
    private final RiskAnalysisRepository riskAnalysisRepository;

    /**
     * 리스크 분석 프롬프트 생성, Gemini 호출, 파싱, 엔티티 변환 및 저장
     * @param variables 프롬프트 변수 맵
     * @param portfolio 연관 포트폴리오 엔티티
     * @return 저장된 RiskAnalysis 엔티티
     */
    @Transactional
    public RiskAnalysis analyzeAndSave(Map<String, String> variables, Portfolio portfolio) throws Exception {
        String prompt = promptTemplateService.createRiskAnalysisPrompt(variables);

        String response = geminiApiClient.generateContent(prompt);

        RiskAnalysisResponse parsed = geminiResponseParser.parseRiskAnalysis(response);

        RiskAnalysis entity = riskAnalysisMapper.toEntity(parsed, portfolio);
        
        return riskAnalysisRepository.save(entity);
    }
} 