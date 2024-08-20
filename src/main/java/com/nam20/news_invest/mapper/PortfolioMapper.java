package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PortfolioResponse;
import com.nam20.news_invest.entity.Portfolio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortfolioMapper {

    private final ModelMapper modelMapper;

    public PortfolioResponse toDto(Portfolio portfolio) {
        return modelMapper.map(portfolio, PortfolioResponse.class);
    }
}
