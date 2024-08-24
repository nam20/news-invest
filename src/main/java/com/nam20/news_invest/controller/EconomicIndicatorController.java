package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.EconomicIndicatorResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.service.EconomicIndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/economic-indicators")
@RequiredArgsConstructor
public class EconomicIndicatorController {

    private final EconomicIndicatorService economicIndicatorService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<EconomicIndicatorResponse>> retrieveEconomicIndicators(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(economicIndicatorService.retrieveFredEconomicData(page, PAGE_SIZE));
    }
}