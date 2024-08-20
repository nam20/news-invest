package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.CurrentMarketPriceResponse;
import com.nam20.news_invest.entity.CurrentMarketPrice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentMarketPriceMapper {

    private final ModelMapper modelMapper;

    public CurrentMarketPriceResponse toDto(CurrentMarketPrice currentMarketPrice) {
        return modelMapper.map(currentMarketPrice, CurrentMarketPriceResponse.class);
    }
}
