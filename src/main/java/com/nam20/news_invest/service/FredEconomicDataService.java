package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.EconomicIndicator;
import com.nam20.news_invest.repository.EconomicIndicatorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

@Service
public class FredEconomicDataService {

    private final WebClient webClient;
    private final EconomicIndicatorRepository economicIndicatorRepository;

    @Value("${FRED_API_KEY}")
    private String apiKey;

    private final List<String> seriesIds = List.of("UNRATE", "FEDFUNDS", "CPIAUCSL", "GDP", "DGORDER");

    public FredEconomicDataService(WebClient.Builder webClientBuilder,
                                   EconomicIndicatorRepository economicIndicatorRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.stlouisfed.org").build();
        this.economicIndicatorRepository = economicIndicatorRepository;
    }

    public void processAllSeries() {
        seriesIds.forEach(this::getAndSaveEconomicData);
    }

    private void getAndSaveEconomicData(String seriesId) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fred/series/observations")
                        .queryParam("series_id", seriesId)
                        .queryParam("api_key", apiKey)
                        .queryParam("file_type", "json")
                        .queryParam("observation_start", "2020-01-01")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(jsonNode -> Flux.fromIterable(jsonNode.get("observations")))
                .map(observation -> convertToFredEconomicData(observation, seriesId))
                .subscribe(economicIndicatorRepository::save);
    }

    private EconomicIndicator convertToFredEconomicData(JsonNode jsonNode, String seriesId) {
        String date = jsonNode.get("date").asText();
        Double value = jsonNode.get("value").asDouble();

        EconomicIndicator economicIndicator = findOrCreateFredEconomicData(LocalDate.parse(date));

        switch (seriesId) {
            case "UNRATE":
                economicIndicator.setUnrate(value);
                break;
            case "FEDFUNDS":
                economicIndicator.setFedFunds(value);
                break;
            case "CPIAUCSL":
                economicIndicator.setCpi(value);
                break;
            case "GDP":
                economicIndicator.setGdp(value);
                break;
            case "DGORDER":
                economicIndicator.setDgOrder(value);
                break;
        }

        return economicIndicator;
    }

    private EconomicIndicator findOrCreateFredEconomicData(LocalDate date) {
        return economicIndicatorRepository.findByDate(date)
                .orElseGet(() -> economicIndicatorRepository.save(EconomicIndicator.builder()
                        .date(date)
                        .build()));
    }
}
