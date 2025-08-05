package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.EconomicIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EconomicIndicatorRepository extends JpaRepository<EconomicIndicator, Long> {
    Optional<EconomicIndicator> findByDate(LocalDate date);
    Optional<EconomicIndicator> findTopByDateBeforeOrderByDateDesc(LocalDate date);
}
