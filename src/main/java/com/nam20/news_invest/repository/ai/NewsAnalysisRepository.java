package com.nam20.news_invest.repository.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nam20.news_invest.entity.ai.NewsAnalysis;

public interface NewsAnalysisRepository extends JpaRepository<NewsAnalysis, Long> {
}
