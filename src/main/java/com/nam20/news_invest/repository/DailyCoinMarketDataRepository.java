package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyCoinMarketData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailyCoinMarketDataRepository extends JpaRepository<DailyCoinMarketData, Long> {
    List<DailyCoinMarketData> findByName(String name);
    Optional<DailyCoinMarketData> findFirstByNameOrderByDateDesc(String name);
}
