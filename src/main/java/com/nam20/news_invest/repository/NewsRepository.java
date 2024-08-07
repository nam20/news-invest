package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
