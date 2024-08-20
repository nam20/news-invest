package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.CurrentMarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentMarketPriceRepository extends JpaRepository<CurrentMarketPrice, Long> {
    Optional<CurrentMarketPrice> findByTypeAndSymbol(String type, String symbol);
}
