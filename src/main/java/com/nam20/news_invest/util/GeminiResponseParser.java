package com.nam20.news_invest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nam20.news_invest.dto.NewsAnalysisResponse;
import com.nam20.news_invest.dto.InvestmentStrategyResponse;
import com.nam20.news_invest.dto.RiskAnalysisResponse;
import org.springframework.stereotype.Component;

@Component
public class GeminiResponseParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 뉴스 분석 응답 파싱
    public NewsAnalysisResponse parseNewsAnalysis(String json) throws Exception {
        return objectMapper.readValue(json, NewsAnalysisResponse.class);
    }

    // 투자 전략 응답 파싱
    public InvestmentStrategyResponse parseInvestmentStrategy(String json) throws Exception {
        return objectMapper.readValue(json, InvestmentStrategyResponse.class);
    }

    // 리스크 분석 응답 파싱
    public RiskAnalysisResponse parseRiskAnalysis(String json) throws Exception {
        return objectMapper.readValue(json, RiskAnalysisResponse.class);
    }
} 