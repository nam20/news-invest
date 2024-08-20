package com.nam20.news_invest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class AssetRequest {
    private final String symbol;
    private final String type;
    private final Double quantity;
    private final Double purchasePrice;
    private final LocalDate purchaseDate;
}
