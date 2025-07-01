package com.nam20.news_invest.mapper.ai;

import com.nam20.news_invest.dto.ai.InvestmentStrategyResponse;
import com.nam20.news_invest.entity.ai.InvestmentStrategy;
import com.nam20.news_invest.entity.Portfolio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InvestmentStrategyMapper {
    /**
     * InvestmentStrategyResponse DTO를 InvestmentStrategy 엔티티로 변환
     * @param dto InvestmentStrategyResponse DTO
     * @param portfolio 연관 Portfolio 엔티티 (직접 주입)
     * @return InvestmentStrategy 엔티티
     */
    @Mapping(target = "portfolio", source = "portfolio")
    InvestmentStrategy toEntity(InvestmentStrategyResponse dto, Portfolio portfolio);

    // 중첩 클래스 매핑 메서드들
    InvestmentStrategy.Recommendation toEntity(InvestmentStrategyResponse.Recommendation dto);
    InvestmentStrategy.PortfolioAdjustment toEntity(InvestmentStrategyResponse.PortfolioAdjustment dto);

    // 리스트 매핑은 MapStruct가 자동 처리
    List<InvestmentStrategy.Recommendation> toRecommendationList(List<InvestmentStrategyResponse.Recommendation> dtoList);
    List<InvestmentStrategy.PortfolioAdjustment> toPortfolioAdjustmentList(List<InvestmentStrategyResponse.PortfolioAdjustment> dtoList);
} 