package com.nam20.news_invest.repository.ai;

import com.nam20.news_invest.entity.ai.PricePrediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePredictionRepository extends JpaRepository<PricePrediction, Long> {
} 