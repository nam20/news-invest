package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.CurrentMarketPriceResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.enums.AssetType;
import com.nam20.news_invest.service.CurrentMarketPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/current-market-prices")
@RequiredArgsConstructor
public class CurrentMarketPriceController {

    private final CurrentMarketPriceService currentMarketPriceService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<CurrentMarketPriceResponse>> retrieveCurrentMarketPrices(
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(currentMarketPriceService.retrieveCurrentMarketPrices(page, PAGE_SIZE));
    }

    @GetMapping("/{type}/{symbol}")
    public ResponseEntity<CurrentMarketPriceResponse> retrieveCurrentMarketPrice(
            @PathVariable AssetType type, @PathVariable String symbol
    ) {
        return ResponseEntity.ok(currentMarketPriceService.retrieveCurrentMarketPrice(type, symbol));
    }
}
