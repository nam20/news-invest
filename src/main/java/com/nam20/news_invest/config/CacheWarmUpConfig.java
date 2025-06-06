package com.nam20.news_invest.config;

import com.nam20.news_invest.service.NewsArticleService;
import com.nam20.news_invest.service.CurrentMarketPriceService;
import com.nam20.news_invest.service.DailyCoinMetricService;
import com.nam20.news_invest.service.DailyStockMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CacheWarmUpConfig {
    private final NewsArticleService newsArticleService;
    private final CurrentMarketPriceService currentMarketPriceService;
    private final DailyCoinMetricService dailyCoinMetricService;
    private final DailyStockMetricService dailyStockMetricService;

    @EventListener(ApplicationReadyEvent.class)
    public void warmUpCache() {
        newsArticleService.retrieveNewsArticles(0, 20);
        currentMarketPriceService.retrieveCurrentMarketPrices(0, 20);
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        dailyCoinMetricService.retrieveDailyCoinMarketData("bitcoin", 0, 30, startDate, endDate);
        dailyStockMetricService.retrieveDailyStockPrices("QQQ", 0, 30, startDate, endDate);
    }
} 