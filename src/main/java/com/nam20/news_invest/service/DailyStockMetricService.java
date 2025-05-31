package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.DailyStockMetric;
import com.nam20.news_invest.mapper.DailyMarketPriceMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.DailyStockMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyStockMetricService {

    private final DailyStockMetricRepository dailyStockMetricRepository;
    private final DailyMarketPriceMapper dailyMarketPriceMapper;
    private final PaginationMetaMapper paginationMetaMapper;

    @Cacheable(value = "dailyStockPrices", key = "#symbol + '-' + #page + '-' + #size + '-' + #startDate + '-' + #endDate")
    public PaginationResponse<DailyMarketPriceResponse> retrieveDailyStockPrices(
            String symbol, int page, int size, LocalDate startDate, LocalDate endDate
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());
        Page<DailyStockMetric> dailyStockMetricsPage = dailyStockMetricRepository.findBySymbolAndDateBetween(symbol, startDate, endDate, pageable);

        List<DailyMarketPriceResponse> dailyMarketPriceResponses = dailyStockMetricsPage
                .getContent()
                .stream()
                .map(dailyMarketPriceMapper::stockToDto)
                .toList();

        return new PaginationResponse<>(dailyMarketPriceResponses,
                paginationMetaMapper.toPaginationMeta(dailyStockMetricsPage));
    }
}
