package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyStockMetric;
import com.nam20.news_invest.mapper.DailyMarketPriceMapper;
import com.nam20.news_invest.repository.DailyStockMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyStockMetricService {

    private final DailyStockMetricRepository dailyStockMetricRepository;
    private final DailyMarketPriceMapper dailyMarketPriceMapper;

    public List<DailyMarketPriceResponse> retrieveDailyStockPrices(String symbol) {
        List<DailyStockMetric> prices = dailyStockMetricRepository.findBySymbol(symbol);

        return prices.stream()
                .map(dailyMarketPriceMapper::stockToDto)
                .toList();
    }
}
