package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyStockMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailyStockMetricRepository extends JpaRepository<DailyStockMetric, Long> {
    List<DailyStockMetric> findBySymbol(String symbol);
    Optional<DailyStockMetric> findFirstBySymbolOrderByDateDesc(String symbol);
}
