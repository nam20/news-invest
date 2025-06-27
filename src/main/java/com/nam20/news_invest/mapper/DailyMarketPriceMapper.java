package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyCoinMetric;
import com.nam20.news_invest.entity.DailyStockMetric;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DailyMarketPriceMapper {
    
    @Mapping(source = "closePrice", target = "price")
    DailyMarketPriceResponse stockToDto(DailyStockMetric dailyStockMetric);

    DailyMarketPriceResponse coinToDto(DailyCoinMetric dailyCoinMetric);
}
