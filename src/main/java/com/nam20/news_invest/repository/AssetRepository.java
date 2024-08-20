package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByPortfolioId(Long portfolioId);
    Optional<Asset> findByPortfolioIdAndSymbolAndType(Long portfolioId, String symbol, String type);
}
