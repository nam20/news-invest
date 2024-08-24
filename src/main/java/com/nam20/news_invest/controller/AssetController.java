package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.AssetResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
    private static final int PAGE_SIZE = 20;

    @GetMapping("/{portfolioId}/assets")
    public ResponseEntity<PaginationResponse<AssetResponse>> retrieveAssets(
            @PathVariable Long portfolioId,
            @PathVariable(required = false) int page
    ) {
        return ResponseEntity.ok(assetService.retrieveAssets(portfolioId, page, PAGE_SIZE));
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
