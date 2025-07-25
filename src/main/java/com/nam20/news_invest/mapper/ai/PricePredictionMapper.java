package com.nam20.news_invest.mapper.ai;

import com.nam20.news_invest.dto.ai.PricePredictionResponse;
import com.nam20.news_invest.entity.ai.PricePrediction;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PricePredictionMapper {
    /**
     * PricePredictionResponse DTO를 PricePrediction 엔티티로 변환
     * @param dto PricePredictionResponse DTO
     * @return PricePrediction 엔티티
     */
    PricePrediction toEntity(PricePredictionResponse dto);

    // 중첩 클래스 매핑 메서드들
    PricePrediction.Prediction toEntity(PricePredictionResponse.Prediction dto);
    PricePredictionResponse.Prediction toDto(PricePrediction.Prediction entity);

    // 리스트 매핑 (MapStruct가 자동 처리)
    List<String> rationaleListToStringList(List<PricePrediction.PredictionRationale> rationaleList);
    List<String> noteListToStringList(List<PricePrediction.PredictionNote> noteList);
} 