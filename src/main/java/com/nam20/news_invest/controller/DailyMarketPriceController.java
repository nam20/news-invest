package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.service.DailyCoinMetricService;
import com.nam20.news_invest.service.DailyStockMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-market-prices")
@RequiredArgsConstructor
public class DailyMarketPriceController {

    private final DailyStockMetricService dailyStockMetricService;
    private final DailyCoinMetricService dailyCoinMetricService;
    private static final int PAGE_SIZE = 20;

    @GetMapping("{type}/{symbol}")
    public ResponseEntity<PaginationResponse<DailyMarketPriceResponse>> retrieveDailyMarketPrices(
            @PathVariable String type, @PathVariable String symbol,
            @RequestParam(defaultValue = "0") int page
    ) {
        if (type.equals("stock")) {
            return ResponseEntity.ok(dailyStockMetricService.retrieveDailyStockPrices(symbol, page, PAGE_SIZE));
        } else {
            return ResponseEntity.ok(dailyCoinMetricService.retrieveDailyCoinMarketData(symbol, page, PAGE_SIZE));
        }
    }
}
