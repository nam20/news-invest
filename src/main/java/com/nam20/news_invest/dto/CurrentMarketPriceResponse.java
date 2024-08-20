package com.nam20.news_invest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentMarketPriceResponse {
    private Long id;
    private String symbol;
    private String type;
    private Double price;
    private String updatedAt;
}
