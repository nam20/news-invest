package com.nam20.news_invest.repository.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nam20.news_invest.entity.ai.RiskAnalysis;

public interface RiskAnalysisRepository extends JpaRepository<RiskAnalysis, Long> {
}
