package com.nam20.news_invest.controller;

import com.nam20.news_invest.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api-test")
public class OpenApiTestController {

    private final BingNewsService bingNewsService;
    private final CoinGeckoCryptoService coinGeckoCryptoService;
    private final AlphaVantageStockService alphaVantageStockService;
    private final FredEconomicDataService fredEconomicDataService;
    private final DeeplTranslateService deeplTranslateService;

    @GetMapping("/bing-news")
    public void getAndSaveBingNews() {
        bingNewsService.processAllSearchQueries();
    }

    @GetMapping("/coin-gecko-crypto")
    public void getAndSaveCoinGeckoCrypto() {
        coinGeckoCryptoService.processAllCoins();
    }

    @GetMapping("/alpha-vantage-stock")
    public void getAndSaveAlphaVantageStock() {
        alphaVantageStockService.processAllSymbols();
    }

    @GetMapping("/fred-economic-data")
    public void getAndSaveFredEconomicData() {
        fredEconomicDataService.processAllSeries();
    }

    @GetMapping("/deepl-translate")
    public void testDeeplTranslate() {
        List<String> texts = List.of("Hello, world!", "Goodbye, world!");
        deeplTranslateService.translateTexts(texts).subscribe();
    }
}
