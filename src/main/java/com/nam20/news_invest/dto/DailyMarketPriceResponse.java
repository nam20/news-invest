package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyMarketPriceResponse {
    private LocalDate date;
    private Double price;
}
