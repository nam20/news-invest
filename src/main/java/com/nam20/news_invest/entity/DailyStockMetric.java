package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "daily_stock_metrics",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"symbol", "date"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyStockMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private LocalDate date;

    private Double openPrice;

    private Double highPrice;

    private Double lowPrice;

    private Double closePrice;

    private Long volume;

    @Builder
    public DailyStockMetric(String symbol, LocalDate date, Double openPrice,
                            Double highPrice, Double lowPrice, Double closePrice, Long volume) {
        this.symbol = symbol;
        this.date = date;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }
}
