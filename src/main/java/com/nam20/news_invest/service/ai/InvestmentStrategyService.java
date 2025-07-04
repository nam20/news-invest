package com.nam20.news_invest.service.ai;

import com.nam20.news_invest.dto.ai.InvestmentStrategyResponse;
import com.nam20.news_invest.entity.Portfolio;
import com.nam20.news_invest.entity.ai.InvestmentStrategy;
import com.nam20.news_invest.mapper.ai.InvestmentStrategyMapper;
import com.nam20.news_invest.repository.ai.InvestmentStrategyRepository;
import com.nam20.news_invest.service.PromptTemplateService;
import com.nam20.news_invest.util.GeminiApiClient;
import com.nam20.news_invest.util.GeminiResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvestmentStrategyService {
    private final PromptTemplateService promptTemplateService;
    private final GeminiApiClient geminiApiClient;
    private final GeminiResponseParser geminiResponseParser;
    private final InvestmentStrategyMapper investmentStrategyMapper;
    private final InvestmentStrategyRepository investmentStrategyRepository;

    /**
     * 투자 전략 프롬프트 생성, Gemini 호출, 파싱, 엔티티 변환 및 저장
     * @param variables 프롬프트 변수 맵
     * @param portfolio 연관 포트폴리오 엔티티
     * @return 저장된 InvestmentStrategy 엔티티
     */
    @Transactional
    public InvestmentStrategy analyzeAndSave(Map<String, String> variables, Portfolio portfolio) {
        String prompt = promptTemplateService.createInvestmentStrategyPrompt(variables);

        String response = geminiApiClient.generateContent(prompt);

        InvestmentStrategyResponse parsed = geminiResponseParser.parseInvestmentStrategy(response);

        InvestmentStrategy entity = investmentStrategyMapper.toEntity(parsed, portfolio);
        
        return investmentStrategyRepository.save(entity);
    }
} 