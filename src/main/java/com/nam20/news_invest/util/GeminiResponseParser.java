package com.nam20.news_invest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nam20.news_invest.dto.ai.InvestmentStrategyResponse;
import com.nam20.news_invest.dto.ai.NewsAnalysisResponse;
import com.nam20.news_invest.dto.ai.PricePredictionResponse;
import com.nam20.news_invest.dto.ai.RiskAnalysisResponse;

import org.springframework.stereotype.Component;

@Component
public class GeminiResponseParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 뉴스 분석 응답 파싱
    public NewsAnalysisResponse parseNewsAnalysis(String json) {
        try {
            return objectMapper.readValue(json, NewsAnalysisResponse.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("AI 응답 파싱 실패", e);
        }
    }

    // 투자 전략 응답 파싱
    public InvestmentStrategyResponse parseInvestmentStrategy(String json) {
        try {
            return objectMapper.readValue(json, InvestmentStrategyResponse.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("AI 응답 파싱 실패", e);
        }
    }

    // 리스크 분석 응답 파싱
    public RiskAnalysisResponse parseRiskAnalysis(String json) {
        try {
            return objectMapper.readValue(json, RiskAnalysisResponse.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("AI 응답 파싱 실패", e);
        }
    }

    // 가격 예측 응답 파싱
    public PricePredictionResponse parsePricePrediction(String json) {
        try {
            return objectMapper.readValue(json, PricePredictionResponse.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("AI 응답 파싱 실패", e);
        }
    }
} 