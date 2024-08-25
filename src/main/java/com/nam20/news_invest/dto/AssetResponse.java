package com.nam20.news_invest.dto;

import com.nam20.news_invest.enums.AssetType;
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
    private AssetType type;
    private Double quantity;
    private Double purchasePrice;
    private LocalDateTime lastPurchasedAt;
}
