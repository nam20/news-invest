package com.nam20.news_invest.entity;

import com.nam20.news_invest.enums.AssetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "assets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private AssetType type;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double averagePurchasePrice;

    @Column(nullable = false)
    private LocalDateTime lastPurchasedAt;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades;

    @Builder
    public Asset(String symbol, AssetType type, Double quantity,
                 Double averagePurchasePrice, LocalDateTime lastPurchasedAt, Portfolio portfolio) {
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.averagePurchasePrice = averagePurchasePrice;
        this.lastPurchasedAt = lastPurchasedAt;
        this.portfolio = portfolio;
    }

    public void updateAveragePurchasePriceAndQuantity(Double averagePurchasePrice, Double quantity) {
        this.averagePurchasePrice = averagePurchasePrice;
        this.quantity = quantity;
    }
}
