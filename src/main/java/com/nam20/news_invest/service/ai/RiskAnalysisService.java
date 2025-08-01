package com.nam20.news_invest.service.ai;

import com.nam20.news_invest.dto.ai.RiskAnalysisResponse;
import com.nam20.news_invest.entity.Portfolio;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.entity.ai.RiskAnalysis;
import com.nam20.news_invest.event.RiskExceededEvent;
import com.nam20.news_invest.mapper.ai.RiskAnalysisMapper;
import com.nam20.news_invest.repository.ai.RiskAnalysisRepository;
import com.nam20.news_invest.service.PromptTemplateService;
import com.nam20.news_invest.util.GeminiApiClient;
import com.nam20.news_invest.util.GeminiResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiskAnalysisService {
    private final PromptTemplateService promptTemplateService;
    private final GeminiApiClient geminiApiClient;
    private final GeminiResponseParser geminiResponseParser;
    private final RiskAnalysisMapper riskAnalysisMapper;
    private final RiskAnalysisRepository riskAnalysisRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${risk.score.threshold:80}")
    private int riskScoreThreshold;

    /**
     * 리스크 분석 프롬프트 생성, Gemini 호출, 파싱, 엔티티 변환 및 저장
     * @param variables 프롬프트 변수 맵
     * @param portfolio 연관 포트폴리오 엔티티
     * @return 저장된 RiskAnalysis 엔티티
     */
    @Transactional
    public RiskAnalysis analyzeAndSave(Map<String, String> variables, Portfolio portfolio) {
        String prompt = promptTemplateService.createRiskAnalysisPrompt(variables);

        String response = geminiApiClient.generateContent(prompt);

        RiskAnalysisResponse parsed = geminiResponseParser.parseRiskAnalysis(response);

        RiskAnalysis entity = riskAnalysisMapper.toEntity(parsed, portfolio);

        RiskAnalysis savedEntity = riskAnalysisRepository.save(entity);

        // 리스크 점수 확인 및 이벤트 발행
        checkRiskAndPublishEvent(savedEntity);

        return savedEntity;
    }

    private void checkRiskAndPublishEvent(RiskAnalysis riskAnalysis) {
        Optional.ofNullable(riskAnalysis.getOverallRiskAnalysis())
                .ifPresent(overall -> {
                    if (overall.getScore() > riskScoreThreshold) {
                        User user = riskAnalysis.getPortfolio().getUser();
                        String message = String.format(
                                "포트폴리오의 리스크 점수가 %d점으로, 설정된 임계값 %d점을 초과했습니다. 평가: %s",
                                overall.getScore(),
                                riskScoreThreshold,
                                overall.getAssessment()
                        );
                        RiskExceededEvent event = new RiskExceededEvent(
                                this,
                                user,
                                "RISK_SCORE_THRESHOLD_EXCEEDED",
                                message
                        );
                        eventPublisher.publishEvent(event);
                    }
                });
    }
}
 