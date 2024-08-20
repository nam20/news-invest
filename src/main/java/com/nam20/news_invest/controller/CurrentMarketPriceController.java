package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.CurrentMarketPriceResponse;
import com.nam20.news_invest.service.CurrentMarketPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/current-market-prices")
@RequiredArgsConstructor
public class CurrentMarketPriceController {

    private final CurrentMarketPriceService currentMarketPriceService;

    @GetMapping
    public ResponseEntity<List<CurrentMarketPriceResponse>> retrieveCurrentMarketPrices() {
        return ResponseEntity.ok(currentMarketPriceService.retrieveCurrentMarketPrices());
    }

    @GetMapping("/{type}/{symbol}")
    public ResponseEntity<CurrentMarketPriceResponse> retrieveCurrentMarketPrice(@PathVariable String type, @PathVariable String symbol) {
        return ResponseEntity.ok(currentMarketPriceService.retrieveCurrentMarketPrice(type, symbol));
    }
}
