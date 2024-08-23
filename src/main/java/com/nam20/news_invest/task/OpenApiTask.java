package com.nam20.news_invest.task;

import com.nam20.news_invest.service.AlphaVantageStockService;
import com.nam20.news_invest.service.BingNewsService;
import com.nam20.news_invest.service.CoinGeckoCryptoService;
import com.nam20.news_invest.service.FredEconomicDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenApiTask {

    private final AlphaVantageStockService alphaVantageStockService;
    private final BingNewsService bingNewsService;
    private final CoinGeckoCryptoService coinGeckoCryptoService;
    private final FredEconomicDataService fredEconomicDataService;

    @Scheduled(cron = "0 0 22 * * *")
    public void fetchOpenApiData() {
        alphaVantageStockService.processAllSymbols();
        bingNewsService.processAllSearchQueries();
        coinGeckoCryptoService.processAllCoins();
        fredEconomicDataService.processAllSeries();
    }
}
