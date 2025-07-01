package com.nam20.news_invest.mapper.ai;

import com.nam20.news_invest.dto.ai.RiskAnalysisResponse;
import com.nam20.news_invest.entity.ai.RiskAnalysis;
import com.nam20.news_invest.entity.Portfolio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RiskAnalysisMapper {
    /**
     * RiskAnalysisResponse DTO를 RiskAnalysis 엔티티로 변환
     * @param dto RiskAnalysisResponse DTO
     * @param portfolio 연관 Portfolio 엔티티 (직접 주입)
     * @return RiskAnalysis 엔티티
     */
    @Mapping(target = "portfolio", source = "portfolio")
    RiskAnalysis toEntity(RiskAnalysisResponse dto, Portfolio portfolio);

    // 중첩 클래스 매핑 메서드들
    RiskAnalysis.OverallRiskAnalysis toEntity(RiskAnalysisResponse.OverallRiskAnalysis dto);
    RiskAnalysis.RiskFactor toEntity(RiskAnalysisResponse.RiskFactor dto);
    RiskAnalysis.RiskContribution toEntity(RiskAnalysisResponse.RiskContribution dto);
    RiskAnalysis.RiskMitigationPlan toEntity(RiskAnalysisResponse.RiskMitigationPlan dto);

    // 리스트 매핑은 MapStruct가 자동 처리
    List<RiskAnalysis.RiskFactor> toRiskFactorList(List<RiskAnalysisResponse.RiskFactor> dtoList);
    List<RiskAnalysis.RiskContribution> toRiskContributionList(List<RiskAnalysisResponse.RiskContribution> dtoList);
    List<RiskAnalysis.RiskMitigationPlan> toRiskMitigationPlanList(List<RiskAnalysisResponse.RiskMitigationPlan> dtoList);
} 