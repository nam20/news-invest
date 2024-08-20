package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.AssetRequest;
import com.nam20.news_invest.dto.AssetResponse;
import com.nam20.news_invest.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/{portfolioId}/assets")
    public ResponseEntity<List<AssetResponse>> retrieveAssets(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(assetService.retrieveAssets(portfolioId));
    }

    @GetMapping("/{portfolioId}/assets/{assetId}")
    public ResponseEntity<AssetResponse> retrieveAsset(@PathVariable Long portfolioId, @PathVariable Long assetId) {
        return ResponseEntity.ok(assetService.retrieveAsset(portfolioId, assetId));
    }

    @DeleteMapping("/{portfolioId}/assets/{assetId}")
    public void deleteAsset(@PathVariable Long portfolioId, @PathVariable Long assetId) {
        assetService.deleteAsset(portfolioId, assetId);
    }
}
