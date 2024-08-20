package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.TradeResponse;
import com.nam20.news_invest.entity.Trade;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeMapper {

    private final ModelMapper modelMapper;

    public TradeResponse toDto(Trade trade) {
        return modelMapper.map(trade, TradeResponse.class);
    }
}
