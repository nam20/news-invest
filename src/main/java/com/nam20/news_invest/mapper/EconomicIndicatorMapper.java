package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.EconomicIndicatorResponse;
import com.nam20.news_invest.entity.EconomicIndicator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EconomicIndicatorMapper {

    private final ModelMapper modelMapper;

    public EconomicIndicatorResponse toDto(EconomicIndicator economicIndicator) {
        return modelMapper.map(economicIndicator, EconomicIndicatorResponse.class);
    }
}
