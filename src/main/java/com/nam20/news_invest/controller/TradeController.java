package com.nam20.news_invest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolios")
public class TradeController {

    @PostMapping("/{portfolioId}/assets/{assetId}/buy")
    public void buyAsset(@PathVariable Long portfolioId, @PathVariable Long assetId) {
        // TODO
    }

    @PostMapping("/{portfolioId}/assets/{assetId}/sell")
    public void sellAsset(@PathVariable Long portfolioId, @PathVariable Long assetId) {
        // TODO
    }
}
