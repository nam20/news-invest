package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.CurrentMarketPriceResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.CurrentMarketPrice;
import com.nam20.news_invest.enums.AssetType;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.CurrentMarketPriceMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.CurrentMarketPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrentMarketPriceService {

    private final CurrentMarketPriceRepository currentMarketPriceRepository;
    private final CurrentMarketPriceMapper currentMarketPriceMapper;
    private final PaginationMetaMapper paginationMetaMapper;

    public PaginationResponse<CurrentMarketPriceResponse> retrieveCurrentMarketPrices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<CurrentMarketPrice> currentMarketPricesPage = currentMarketPriceRepository.findAll(pageable);

        List<CurrentMarketPriceResponse> currentMarketPriceResponses = currentMarketPricesPage
                .getContent()
                .stream()
                .map(currentMarketPriceMapper::toDto)
                .toList();

        return new PaginationResponse<>(currentMarketPriceResponses,
                paginationMetaMapper.toPaginationMeta(currentMarketPricesPage));
    }

    public CurrentMarketPriceResponse retrieveCurrentMarketPrice(AssetType type, String symbol) {
        CurrentMarketPrice currentMarketPrice = currentMarketPriceRepository.findByTypeAndSymbol(type, symbol)
                .orElseThrow(() -> new ResourceNotFoundException("type: " + type + ", symbol: " + symbol));

        return currentMarketPriceMapper.toDto(currentMarketPrice);
    }
}
