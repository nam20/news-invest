package com.nam20.news_invest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class AssetController {

    @GetMapping("/{portfolioId}/assets")
    public void retrieveAssets(@PathVariable Long portfolioId) {
        // TODO
    }

    @GetMapping("/{portfolioId}/assets/{assetId}")
    public void retrieveAsset(@PathVariable Long portfolioId, @PathVariable Long assetId) {
        // TODO
    }

    @PostMapping("/{portfolioId}/assets")
    public void createAsset(@PathVariable Long portfolioId) {
        // TODO
    }

    @DeleteMapping("/{portfolioId}/assets/{assetId}")
    public void deleteAsset(@PathVariable Long portfolioId, @PathVariable Long assetId) {
        // TODO
    }
}
