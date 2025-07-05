package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.dto.PortfolioRequest;
import com.nam20.news_invest.dto.PortfolioResponse;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.entity.ai.InvestmentStrategy;
import com.nam20.news_invest.entity.ai.RiskAnalysis;
import com.nam20.news_invest.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<PortfolioResponse>> retrievePortfolios(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(portfolioService.retrievePortfolios(user, page, PAGE_SIZE));
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

    /**
     * 포트폴리오 AI 투자 전략 분석 요청 API
     * @param portfolioId 분석할 포트폴리오 ID
     * @return 생성된 InvestmentStrategy 결과
     */
    @PostMapping("/{portfolioId}/investment-strategy")
    public ResponseEntity<InvestmentStrategy> analyzeInvestmentStrategy(
            @PathVariable Long portfolioId
    ) {
        InvestmentStrategy result = portfolioService.analyzeInvestmentStrategy(portfolioId);
        return ResponseEntity.ok(result);
    }

    /**
     * 포트폴리오 AI 리스크 분석 요청 API
     * @param portfolioId 분석할 포트폴리오 ID
     * @return 생성된 RiskAnalysis 결과
     */
    @PostMapping("/{portfolioId}/risk-analysis")
    public ResponseEntity<RiskAnalysis> analyzeRiskAnalysis(
            @PathVariable Long portfolioId
    ) {
        RiskAnalysis result = portfolioService.analyzeRiskAnalysis(portfolioId);
        return ResponseEntity.ok(result);
    }
}
