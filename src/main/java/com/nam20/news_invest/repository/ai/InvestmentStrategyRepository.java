package com.nam20.news_invest.repository.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nam20.news_invest.entity.ai.InvestmentStrategy;

public interface InvestmentStrategyRepository extends JpaRepository<InvestmentStrategy, Long> {
}
