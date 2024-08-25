package com.nam20.news_invest.dto;

import com.nam20.news_invest.enums.AssetType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class TradeRequest {
    private final String symbol;
    private final AssetType assetType;
    private final Double quantity;
    private final Double price;
}
