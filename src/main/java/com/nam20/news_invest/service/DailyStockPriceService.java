package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyStockPrice;
import com.nam20.news_invest.mapper.DailyMarketPriceMapper;
import com.nam20.news_invest.repository.DailyStockPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyStockPriceService {

    private final DailyStockPriceRepository dailyStockPriceRepository;
    private final DailyMarketPriceMapper dailyMarketPriceMapper;

    public List<DailyMarketPriceResponse> retrieveDailyStockPrices(String symbol) {
        List<DailyStockPrice> prices = dailyStockPriceRepository.findBySymbol(symbol);

        return prices.stream()
                .map(dailyMarketPriceMapper::stockToDto)
                .toList();
    }
}
