package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyStockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyStockPriceRepository extends JpaRepository<DailyStockPrice, Long> {
    List<DailyStockPrice> findBySymbol(String symbol);
}
