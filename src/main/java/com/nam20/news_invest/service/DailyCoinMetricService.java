package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyCoinMetric;
import com.nam20.news_invest.mapper.DailyMarketPriceMapper;
import com.nam20.news_invest.repository.DailyCoinMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCoinMetricService {

    private final DailyCoinMetricRepository dailyCoinMetricRepository;
    private final DailyMarketPriceMapper dailyMarketPriceMapper;

    public List<DailyMarketPriceResponse> retrieveDailyCoinMarketData(String symbol) {
        List<DailyCoinMetric> prices = dailyCoinMetricRepository.findByName(symbol);

        return prices.stream()
                .map(dailyMarketPriceMapper::coinToDto)
                .toList();
    }
}
