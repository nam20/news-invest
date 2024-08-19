package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.EconomicIndicatorResponse;
import com.nam20.news_invest.service.EconomicIndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/economic-indicators")
@RequiredArgsConstructor
public class EconomicIndicatorController {

    private final EconomicIndicatorService economicIndicatorService;

    @GetMapping
    public ResponseEntity<List<EconomicIndicatorResponse>> retrieveEconomicIndicators() {
        return ResponseEntity.ok(economicIndicatorService.retrieveFredEconomicData());
    }
}