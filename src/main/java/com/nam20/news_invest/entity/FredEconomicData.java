package com.nam20.news_invest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FredEconomicData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double unrate;    // 실업률

    private Double fedFunds;  // 연방기금 금리

    private Double cpi;       // 소비자 물가 지수

    private Double gdp;       // 국내총생산

    private Double dgOrder;   // 제조업 신규 주문

    @Builder
    public FredEconomicData(LocalDate date, Double unrate, Double fedFunds,
                            Double cpi, Double gdp, Double dgOrder) {
        this.date = date;
        this.unrate = unrate;
        this.fedFunds = fedFunds;
        this.cpi = cpi;
        this.gdp = gdp;
        this.dgOrder = dgOrder;
    }
}
