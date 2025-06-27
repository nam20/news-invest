package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.CurrentMarketPriceResponse;
import com.nam20.news_invest.entity.CurrentMarketPrice;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentMarketPriceMapper {
    CurrentMarketPriceResponse toDto(CurrentMarketPrice currentMarketPrice);
}
