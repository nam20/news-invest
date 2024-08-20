package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.TradeRequest;
import com.nam20.news_invest.dto.TradeResponse;
import com.nam20.news_invest.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios/{portfolioId}")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/buy")
    public ResponseEntity<TradeResponse> buyAsset(
            @PathVariable Long portfolioId,
            @RequestBody TradeRequest tradeRequest
    ) {
        return ResponseEntity.ok(tradeService.buyAsset(portfolioId, tradeRequest));
    }

    @PostMapping("/sell")
    public ResponseEntity<TradeResponse> sellAsset(
            @PathVariable Long portfolioId,
            @RequestBody TradeRequest tradeRequest
    ) {
        return ResponseEntity.ok(tradeService.sellAsset(portfolioId, tradeRequest));
    }
}
