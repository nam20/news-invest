package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyStockMetric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyStockMetricRepository extends JpaRepository<DailyStockMetric, Long> {
    Page<DailyStockMetric> findBySymbol(String symbol, Pageable pageable);
    Optional<DailyStockMetric> findFirstBySymbolOrderByDateDesc(String symbol);
}
