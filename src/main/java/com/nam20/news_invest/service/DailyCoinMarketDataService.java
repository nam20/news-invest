package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyCoinMarketData;
import com.nam20.news_invest.mapper.DailyMarketPriceMapper;
import com.nam20.news_invest.repository.DailyCoinMarketDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCoinMarketDataService {

    private final DailyCoinMarketDataRepository dailyCoinMarketDataRepository;
    private final DailyMarketPriceMapper dailyMarketPriceMapper;

    public List<DailyMarketPriceResponse> retrieveDailyCoinMarketData(String symbol) {
        List<DailyCoinMarketData> prices = dailyCoinMarketDataRepository.findByName(symbol);

        return prices.stream()
                .map(dailyMarketPriceMapper::coinToDto)
                .toList();
    }
}
