package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.CurrentMarketPrice;
import com.nam20.news_invest.enums.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentMarketPriceRepository extends JpaRepository<CurrentMarketPrice, Long> {
    Optional<CurrentMarketPrice> findByTypeAndSymbol(AssetType type, String symbol);
}
