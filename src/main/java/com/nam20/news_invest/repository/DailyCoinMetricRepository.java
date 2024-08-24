package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.DailyCoinMetric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyCoinMetricRepository extends JpaRepository<DailyCoinMetric, Long> {
    Page<DailyCoinMetric> findByName(String name, Pageable pageable);
    Optional<DailyCoinMetric> findFirstByNameOrderByDateDesc(String name);
}
