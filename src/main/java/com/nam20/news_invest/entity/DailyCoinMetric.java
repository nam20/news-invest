package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "daily_coin_metrics",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "date"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyCoinMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate date;

    private Double price;

    private Double marketCap;

    private Double volume;

    @Builder
    public DailyCoinMetric(String name, LocalDate date, Double price,
                           Double marketCap, Double volume) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.marketCap = marketCap;
        this.volume = volume;
    }
}
