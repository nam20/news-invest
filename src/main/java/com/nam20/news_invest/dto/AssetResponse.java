package com.nam20.news_invest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetResponse {
    private Long id;
    private String symbol;
    private String type;
    private Double quantity;
    private Double purchasePrice;
    private LocalDateTime lastPurchasedAt;
}