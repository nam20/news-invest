package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.AssetResponse;
import com.nam20.news_invest.entity.Asset;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetResponse toDto(Asset asset);    
}
