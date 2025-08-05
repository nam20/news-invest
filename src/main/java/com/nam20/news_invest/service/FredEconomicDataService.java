package com.nam20.news_invest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam20.news_invest.entity.EconomicIndicator;
import com.nam20.news_invest.event.InvestmentOpportunityEvent;
import com.nam20.news_invest.repository.EconomicIndicatorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FredEconomicDataService {

    private final WebClient webClient;
    private final EconomicIndicatorRepository economicIndicatorRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${FRED_API_KEY}")
    private String apiKey;

    private final List<String> seriesIds = List.of("UNRATE", "FEDFUNDS", "CPIAUCSL", "GDP", "DGORDER");

    public FredEconomicDataService(WebClient.Builder webClientBuilder,
                                   EconomicIndicatorRepository economicIndicatorRepository,
                                   ApplicationEventPublisher eventPublisher) {
        this.webClient = webClientBuilder.baseUrl("https://api.stlouisfed.org").build();
        this.economicIndicatorRepository = economicIndicatorRepository;
        this.eventPublisher = eventPublisher;
    }

    @CacheEvict(value = "economicIndicatorData", allEntries = true)
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
                .filter(Objects::nonNull)
                .collectList()
                .doOnNext(economicIndicatorRepository::saveAll)
                .doOnNext(indicators -> {
                    if (!indicators.isEmpty()) {
                        indicators.stream()
                                .max(Comparator.comparing(EconomicIndicator::getDate))
                                .ifPresent(latestIndicator -> checkForInvestmentOpportunities(latestIndicator, seriesId));
                    }
                })
                .subscribe();
    }

    private void checkForInvestmentOpportunities(EconomicIndicator latest, String seriesId) {
        Optional<EconomicIndicator> previousOptional = economicIndicatorRepository.findTopByDateBeforeOrderByDateDesc(latest.getDate());

        if (previousOptional.isEmpty()) {
            return;
        }
        EconomicIndicator previous = previousOptional.get();

        switch (seriesId) {
            case "UNRATE":
                if (previous.getUnrate() != null && latest.getUnrate() < previous.getUnrate() - 0.3) {
                    String details = String.format("실업률이 %.2f%%에서 %.2f%%로 크게 하락했습니다. 고용 시장의 강력한 회복 신호일 수 있습니다.",
                            previous.getUnrate(), latest.getUnrate());
                    eventPublisher.publishEvent(new InvestmentOpportunityEvent(this, details));
                }
                break;
            case "FEDFUNDS":
                if (previous.getFedFunds() != null && !Objects.equals(latest.getFedFunds(), previous.getFedFunds())) {
                    String details = String.format("연방기금 금리가 %.2f%%에서 %.2f%%로 변경되었습니다. 이는 통화 정책의 변화를 의미하며, 시장에 영향을 줄 수 있습니다.",
                            previous.getFedFunds(), latest.getFedFunds());
                    eventPublisher.publishEvent(new InvestmentOpportunityEvent(this, details));
                }
                break;
        }
    }

    private EconomicIndicator convertToFredEconomicData(JsonNode jsonNode, String seriesId) {
        String date = jsonNode.get("date").asText();
        double value;
        try {
            value = jsonNode.get("value").asDouble();
        } catch (NumberFormatException e) {
            // Handle cases where value is not a number, e.g., "."
            return null;
        }


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
