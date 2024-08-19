package com.nam20.news_invest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class EconomicIndicatorResponse {
    private final String date;
    private final Double unrate;
    private final Double fedFunds;
    private final Double cpi;
    private final Double gdp;
    private final Double dgOrder;
}
