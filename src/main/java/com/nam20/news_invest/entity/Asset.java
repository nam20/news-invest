package com.nam20.news_invest.entity;

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
    private String type;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double purchasePrice;

    @Column(nullable = false)
    private LocalDateTime lastPurchasedAt;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades;

    @Builder
    public Asset(String symbol, String type, Double quantity,
                 Double purchasePrice, LocalDateTime lastPurchasedAt, Portfolio portfolio) {
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.lastPurchasedAt = lastPurchasedAt;
        this.portfolio = portfolio;
    }
}
