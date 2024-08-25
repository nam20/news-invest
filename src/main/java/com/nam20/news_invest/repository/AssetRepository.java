package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Asset;
import com.nam20.news_invest.enums.AssetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Page<Asset> findByPortfolioId(Long portfolioId, Pageable pageable);
    Optional<Asset> findByPortfolioIdAndSymbolAndType(Long portfolioId, String symbol, AssetType type);
}
