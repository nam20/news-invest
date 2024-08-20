package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.CurrentMarketPriceResponse;
import com.nam20.news_invest.entity.CurrentMarketPrice;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.CurrentMarketPriceMapper;
import com.nam20.news_invest.repository.CurrentMarketPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrentMarketPriceService {

    private final CurrentMarketPriceRepository currentMarketPriceRepository;
    private final CurrentMarketPriceMapper currentMarketPriceMapper;

    public List<CurrentMarketPriceResponse> retrieveCurrentMarketPrices() {
        return currentMarketPriceRepository.findAll()
                .stream()
                .map(currentMarketPriceMapper::toDto)
                .toList();
    }

    public CurrentMarketPriceResponse retrieveCurrentMarketPrice(String type, String symbol) {
        CurrentMarketPrice currentMarketPrice = currentMarketPriceRepository.findByTypeAndSymbol(type, symbol)
                .orElseThrow(() -> new ResourceNotFoundException("type: " + type + ", symbol: " + symbol));

        return currentMarketPriceMapper.toDto(currentMarketPrice);
    }
}
