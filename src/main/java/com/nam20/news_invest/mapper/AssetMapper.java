package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.AssetResponse;
import com.nam20.news_invest.entity.Asset;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetMapper {

    private final ModelMapper modelMapper;

    public AssetResponse toDto(Asset asset) {
        return modelMapper.map(asset, AssetResponse.class);
    }
}
