package com.nam20.news_invest.dto;

import com.nam20.news_invest.enums.AssetType;
import com.nam20.news_invest.enums.TransactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradeResponse {
    private String symbol;
    private AssetType type;
    private TransactionType transactionType;
    private Double quantity;
    private Double price;
    private LocalDateTime createdAt;
}
