package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.PortfolioRequest;
import com.nam20.news_invest.dto.PortfolioResponse;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> retrievePortfolios(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(portfolioService.retrievePortfolios(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponse> retrievePortfolio(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.retrievePortfolio(id));
    }

    @PostMapping
    public ResponseEntity<PortfolioResponse> createPortfolio(
            @RequestBody PortfolioRequest portfolioRequest
    ) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolioRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioResponse> updatePortfolio(
            @PathVariable Long id, @RequestBody PortfolioRequest portfolioRequest
    ) {
        return ResponseEntity.ok(portfolioService.updatePortfolio(id, portfolioRequest));
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
    }
}
