package com.nam20.news_invest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/economic-indicators")
public class EconomicIndicatorsController {

    @GetMapping
    public void retrieveEconomicIndicators() {
        // TODO
    }
}
