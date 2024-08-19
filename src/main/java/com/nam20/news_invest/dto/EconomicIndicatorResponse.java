package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EconomicIndicatorResponse {
    private String date;
    private Double unrate;
    private Double fedFunds;
    private Double cpi;
    private Double gdp;
    private Double dgOrder;
}
