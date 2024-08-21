package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyCoinMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailyCoinMetricRepository extends JpaRepository<DailyCoinMetric, Long> {
    List<DailyCoinMetric> findByName(String name);
    Optional<DailyCoinMetric> findFirstByNameOrderByDateDesc(String name);
}
