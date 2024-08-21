package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "current_market_prices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CurrentMarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double price;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public CurrentMarketPrice(String symbol, String type, Double price) {
        this.symbol = symbol;
        this.type = type;
        this.price = price;
    }

    public void updatePrice(Double price) {
        this.price = price;
    }
}
