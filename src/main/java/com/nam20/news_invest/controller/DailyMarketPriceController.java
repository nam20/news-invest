package com.nam20.news_invest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/daily-market-prices")
public class DailyMarketPriceController {

    @GetMapping("{type}/{symbol}")
    public void retrieveDailyMarketPrices(@PathVariable String type, @PathVariable String symbol) {
        // TODO
    }
}
