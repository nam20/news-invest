package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PortfolioResponse;
import com.nam20.news_invest.entity.Portfolio;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    PortfolioResponse toDto(Portfolio portfolio);
}
