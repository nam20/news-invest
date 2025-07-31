package com.nam20.news_invest.event;

import com.nam20.news_invest.enums.AssetType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

@Getter
public class PriceSpikeEvent extends ApplicationEvent {

    private final String assetSymbol;
    private final AssetType assetType;
    private final BigDecimal oldPrice;
    private final BigDecimal newPrice;
    private final double percentageChange;

    public PriceSpikeEvent(Object source, String assetSymbol, AssetType assetType, BigDecimal oldPrice, BigDecimal newPrice, double percentageChange) {
        super(source);
        this.assetSymbol = assetSymbol;
        this.assetType = assetType;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.percentageChange = percentageChange;
    }
}
