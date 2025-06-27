package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.TradeResponse;
import com.nam20.news_invest.entity.Trade;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    TradeResponse toDto(Trade trade);
}
