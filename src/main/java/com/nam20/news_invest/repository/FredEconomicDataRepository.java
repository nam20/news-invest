package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.FredEconomicData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FredEconomicDataRepository extends JpaRepository<FredEconomicData, Long> {
    Optional<FredEconomicData> findByDate(LocalDate date);
}
