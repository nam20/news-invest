package com.nam20.news_invest.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PromptTemplateService {
    private static final String NEWS_ANALYSIS_TEMPLATE = "prompt-templates/news_analysis.txt";
    private static final String INVESTMENT_STRATEGY_TEMPLATE = "prompt-templates/investment_strategy.txt";
    private static final String RISK_ANALYSIS_TEMPLATE = "prompt-templates/risk_analysis.txt";
    private static final String PRICE_PREDICTION_TEMPLATE = "prompt-templates/price_prediction.txt";

    /**
     * 템플릿 파일을 읽어와 변수 치환 후 프롬프트를 반환합니다.
     * @param templatePath 템플릿 파일 경로
     * @param variables    치환할 변수 맵 (key: {변수명}, value: 실제 값)
     * @return 완성된 프롬프트 문자열
     */
    private String loadAndFillTemplate(String templatePath, Map<String, String> variables) {
        try {
            // 파일을 한 줄씩 읽어 전체 문자열로 변환
            String template = new BufferedReader(new InputStreamReader(
                    new ClassPathResource(templatePath).getInputStream(), StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));
            // 변수 치환
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                template = template.replace("{" + entry.getKey() + "}", entry.getValue());
            }
            return template;
        } catch (IOException e) {
            return "[에러] 프롬프트 템플릿 파일 로딩 실패: " + e.getMessage();
        }
    }

    /**
     * 뉴스 분석 프롬프트를 생성합니다.
     * @param variables {newsArticleContent}, {userInterestedStocks} 등 변수 맵
     * @return 완성된 프롬프트 문자열
     */
    public String createNewsAnalysisPrompt(Map<String, String> variables) {
        return loadAndFillTemplate(NEWS_ANALYSIS_TEMPLATE, variables);
    }

    /**
     * 투자 전략 프롬프트를 생성합니다.
     * @param variables {userRiskTolerance}, {userInvestmentHorizon}, {userPortfolioDetails}, {totalPortfolioValue}, {marketNewsSummary}, {keyEconomicIndicators} 등 변수 맵
     * @return 완성된 프롬프트 문자열
     */
    public String createInvestmentStrategyPrompt(Map<String, String> variables) {
        return loadAndFillTemplate(INVESTMENT_STRATEGY_TEMPLATE, variables);
    }

    /**
     * 리스크 분석 프롬프트를 생성합니다.
     * @param variables {userPortfolioDetails}, {relevantNegativeNews}, {vixIndex} 등 변수 맵
     * @return 완성된 프롬프트 문자열
     */
    public String createRiskAnalysisPrompt(Map<String, String> variables) {
        return loadAndFillTemplate(RISK_ANALYSIS_TEMPLATE, variables);
    }

    /**
     * 가격 예측 프롬프트를 생성합니다.
     * @param variables {targetAsset}, {historicalPrices}, {marketNewsSummary}, {economicIndicators} 등 변수 맵
     * @return 완성된 프롬프트 문자열
     */
    public String createPricePredictionPrompt(Map<String, String> variables) {
        return loadAndFillTemplate(PRICE_PREDICTION_TEMPLATE, variables);
    }
} 