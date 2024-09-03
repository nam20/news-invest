package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.DailyCoinMetric;
import com.nam20.news_invest.mapper.DailyMarketPriceMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.DailyCoinMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCoinMetricService {

    private final DailyCoinMetricRepository dailyCoinMetricRepository;
    private final DailyMarketPriceMapper dailyMarketPriceMapper;
    private final PaginationMetaMapper paginationMetaMapper;

    public PaginationResponse<DailyMarketPriceResponse> retrieveDailyCoinMarketData(
            String symbol, int page, int size, LocalDate startDate, LocalDate endDate
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<DailyCoinMetric> dailyCoinMetricsPage = dailyCoinMetricRepository.findByNameAndDateBetween(symbol, startDate, endDate, pageable);

        List<DailyMarketPriceResponse> dailyMarketPriceResponses = dailyCoinMetricsPage
                .getContent()
                .stream()
                .map(dailyMarketPriceMapper::coinToDto)
                .toList();

        return new PaginationResponse<>(dailyMarketPriceResponses,
                paginationMetaMapper.toPaginationMeta(dailyCoinMetricsPage));
    }
}
