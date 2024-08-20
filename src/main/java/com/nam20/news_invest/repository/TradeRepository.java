package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
