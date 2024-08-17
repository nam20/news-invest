package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyCoinMarketData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private LocalDate date;

    private Double price;

    private Double marketCap;

    private Double volume;

    @Builder
    public DailyCoinMarketData(String name, LocalDate date, Double price,
                               Double marketCap, Double volume) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.marketCap = marketCap;
        this.volume = volume;
    }
}
