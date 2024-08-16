package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyStockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyStockPriceRepository extends JpaRepository<DailyStockPrice, Long> {
}
