package com.nam20.news_invest.mapper.ai;

import com.nam20.news_invest.dto.ai.NewsAnalysisResponse;
import com.nam20.news_invest.entity.ai.NewsAnalysis;
import com.nam20.news_invest.entity.NewsArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsAnalysisMapper {
    /**
     * NewsAnalysisResponse DTO를 NewsAnalysis 엔티티로 변환
     * @param dto NewsAnalysisResponse DTO
     * @param newsArticle 연관 NewsArticle 엔티티 (직접 주입)
     * @return NewsAnalysis 엔티티
     */
    @Mapping(target = "newsArticle", source = "newsArticle")
    NewsAnalysis toEntity(NewsAnalysisResponse dto, NewsArticle newsArticle);

    // 중첩 클래스 매핑 메서드들
    NewsAnalysis.SentimentAnalysis toEntity(NewsAnalysisResponse.SentimentAnalysis dto);
    NewsAnalysis.OverallSentiment toEntity(NewsAnalysisResponse.OverallSentiment dto);
    NewsAnalysis.MentionedCompanySentiment toEntity(NewsAnalysisResponse.MentionedCompanySentiment dto);
    NewsAnalysis.AffectedEntities toEntity(NewsAnalysisResponse.AffectedEntities dto);
    NewsAnalysis.StockImpactPrediction toEntity(NewsAnalysisResponse.StockImpactPrediction dto);
    NewsAnalysis.PredictionDetail toEntity(NewsAnalysisResponse.PredictionDetail dto);
    NewsAnalysis.Keyword toEntity(NewsAnalysisResponse.Keyword dto);
    NewsAnalysis.InvestmentStrategy toEntity(NewsAnalysisResponse.InvestmentStrategy dto);

    // 리스트 매핑은 MapStruct가 자동 처리
    List<NewsAnalysis.StockImpactPrediction> toEntityList(List<NewsAnalysisResponse.StockImpactPrediction> dtoList);
} 