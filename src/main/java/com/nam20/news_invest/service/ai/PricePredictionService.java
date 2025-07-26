package com.nam20.news_invest.service.ai;

import com.nam20.news_invest.dto.ai.PricePredictionResponse;
import com.nam20.news_invest.entity.Portfolio;
import com.nam20.news_invest.entity.ai.PricePrediction;
import com.nam20.news_invest.mapper.ai.PricePredictionMapper;
import com.nam20.news_invest.repository.ai.PricePredictionRepository;
import com.nam20.news_invest.service.PromptTemplateService;
import com.nam20.news_invest.util.GeminiApiClient;
import com.nam20.news_invest.util.GeminiResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PricePredictionService {
    private final PromptTemplateService promptTemplateService;
    private final GeminiApiClient geminiApiClient;
    private final GeminiResponseParser geminiResponseParser;
    private final PricePredictionMapper pricePredictionMapper;
    private final PricePredictionRepository pricePredictionRepository;

    /**
     * 가격 예측 프롬프트 생성, Gemini 호출, 파싱, 엔티티 변환 및 저장
     * @param variables 프롬프트 변수 맵
     * @param portfolio 연관 포트폴리오 엔티티
     * @return 저장된 PricePrediction 엔티티
     */
    @Transactional
    public PricePrediction analyzeAndSave(Map<String, String> variables, Portfolio portfolio) {
        String prompt = promptTemplateService.createPricePredictionPrompt(variables);

        String response = geminiApiClient.generateContent(prompt);

        PricePredictionResponse parsed = geminiResponseParser.parsePricePrediction(response);

        PricePrediction entity = pricePredictionMapper.toEntity(parsed);

        return pricePredictionRepository.save(entity);
    }
} 