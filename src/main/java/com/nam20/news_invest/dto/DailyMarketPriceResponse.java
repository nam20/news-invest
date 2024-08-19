package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class DailyMarketPriceResponse {
    private final LocalDate date;
    private final Double price;
}
