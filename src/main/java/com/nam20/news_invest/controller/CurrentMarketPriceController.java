package com.nam20.news_invest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/current-market-prices")
public class CurrentMarketPriceController {

    @GetMapping
    public void retrieveCurrentMarketPrices() {
        // TODO
    }

    @GetMapping("/{type}/{symbol}")
    public void retrieveCurrentMarketPrice(@PathVariable String type, @PathVariable String symbol) {
        // TODO
    }
}
