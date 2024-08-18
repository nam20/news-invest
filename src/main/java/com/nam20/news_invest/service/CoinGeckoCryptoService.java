package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.DailyCoinMarketData;
import com.nam20.news_invest.repository.DailyCoinMarketDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class CoinGeckoCryptoService {

    private final WebClient webClient;
    private final DailyCoinMarketDataRepository dailyCoinMarketDataRepository;

    @Value("${COIN_GECKO_API_KEY}")
    private String apiKey;

    private final List<String> coinNames = List.of("bitcoin");

    public CoinGeckoCryptoService(WebClient.Builder webClientBuilder,
                                  DailyCoinMarketDataRepository dailyCoinMarketDataRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.coingecko.com").build();
        this.dailyCoinMarketDataRepository = dailyCoinMarketDataRepository;
    }

    public void processAllCoins() {
        coinNames.forEach(this::getAndSaveCryptoData);
    }

    private void getAndSaveCryptoData(String coinName) {
        String path = """
                /api/v3/coins/%s/market_chart
                """.formatted(coinName);

        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("x_cg_api_key", apiKey)
                        .queryParam("vs_currency", "usd")
                        .queryParam("days", 1)
                        .queryParam("interval", "daily")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::convertToDailyCoinMarketData)
                .subscribe(dailyCoinMarketDataRepository::save);
    }

    private DailyCoinMarketData convertToDailyCoinMarketData(JsonNode jsonNode) {
        long unixTimestamp = jsonNode.get("prices").get(0).get(0).asLong();

        LocalDate date = Instant.ofEpochMilli(unixTimestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return DailyCoinMarketData.builder()
                .date(date)
                .price(jsonNode.get("prices").get(0).get(1).asDouble())
                .marketCap(jsonNode.get("market_caps").get(0).get(1).asDouble())
                .volume(jsonNode.get("total_volumes").get(0).get(1).asDouble())
                .build();
    }
}