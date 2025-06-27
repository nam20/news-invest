package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.EconomicIndicatorResponse;
import com.nam20.news_invest.entity.EconomicIndicator;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EconomicIndicatorMapper {
    EconomicIndicatorResponse toDto(EconomicIndicator economicIndicator);
}
