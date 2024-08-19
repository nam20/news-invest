package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.service.DailyCoinMarketDataService;
import com.nam20.news_invest.service.DailyStockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/daily-market-prices")
@RequiredArgsConstructor
public class DailyMarketPriceController {

    private final DailyStockPriceService dailyStockPriceService;
    private final DailyCoinMarketDataService dailyCoinMarketDataService;

    @GetMapping("{type}/{symbol}")
    public ResponseEntity<List<DailyMarketPriceResponse>> retrieveDailyMarketPrices(
            @PathVariable String type, @PathVariable String symbol
    ) {
        if (type.equals("stock")) {
            return ResponseEntity.ok(dailyStockPriceService.retrieveDailyStockPrices(symbol));
        } else {
            return ResponseEntity.ok(dailyCoinMarketDataService.retrieveDailyCoinMarketData(symbol));
        }
    }
}
