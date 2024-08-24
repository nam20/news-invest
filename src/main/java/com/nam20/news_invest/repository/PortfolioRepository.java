package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Page<Portfolio> findByUserId(Long userId, Pageable pageable);
}
