package com.nam20.news_invest.entity;

import com.nam20.news_invest.enums.AssetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private AssetType assetType;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double price;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Builder
    public Trade(String symbol, AssetType assetType, String transactionType,
                 Double quantity, Double price, Asset asset) {
        this.symbol = symbol;
        this.assetType = assetType;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.price = price;
        this.asset = asset;
    }
}
