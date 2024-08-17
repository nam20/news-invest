package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.FredEconomicData;
import com.nam20.news_invest.repository.FredEconomicDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

@Service
public class FredEconomicDataService {

    private final WebClient webClient;
    private final FredEconomicDataRepository fredEconomicDataRepository;

    @Value("${FRED_API_KEY}")
    private String apiKey;

    private final List<String> seriesIds = List.of("UNRATE", "FEDFUNDS", "CPIAUCSL", "GDP", "DGORDER");

    public FredEconomicDataService(WebClient.Builder webClientBuilder,
                                   FredEconomicDataRepository fredEconomicDataRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.stlouisfed.org").build();
        this.fredEconomicDataRepository = fredEconomicDataRepository;
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
                .subscribe(fredEconomicDataRepository::save);
    }

    private FredEconomicData convertToFredEconomicData(JsonNode jsonNode, String seriesId) {
        String date = jsonNode.get("date").asText();
        Double value = jsonNode.get("value").asDouble();

        FredEconomicData economicData = findOrCreateFredEconomicData(LocalDate.parse(date));

        switch (seriesId) {
            case "UNRATE":
                economicData.setUnrate(value);
                break;
            case "FEDFUNDS":
                economicData.setFedFunds(value);
                break;
            case "CPIAUCSL":
                economicData.setCpi(value);
                break;
            case "GDP":
                economicData.setGdp(value);
                break;
            case "DGORDER":
                economicData.setDgOrder(value);
                break;
        }

        return economicData;
    }

    private FredEconomicData findOrCreateFredEconomicData(LocalDate date) {
        return fredEconomicDataRepository.findByDate(date)
                .orElseGet(() -> fredEconomicDataRepository.save(FredEconomicData.builder()
                        .date(date)
                        .build()));
    }
}
