package com.nam20.news_invest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @GetMapping
    public void retrievePortfolios() {
        // TODO
    }

    @GetMapping("/{id}")
    public void retrievePortfolio(@PathVariable Long id) {
        // TODO
    }

    @PostMapping
    public void createPortfolio() {
        // TODO
    }

    @PutMapping("/{id}")
    public void updatePortfolio(@PathVariable Long id) {
        // TODO
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        // TODO
    }
}
