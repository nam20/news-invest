package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades;

    @Builder
    public Asset(String symbol, String type, Double quantity,
                 Double purchasePrice, LocalDate purchaseDate, Portfolio portfolio) {
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.portfolio = portfolio;
    }
}
