package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyCoinMarketData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyCoinMarketDataRepository extends JpaRepository<DailyCoinMarketData, Long> {
}
