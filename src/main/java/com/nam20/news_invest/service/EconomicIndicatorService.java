package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.EconomicIndicatorResponse;
import com.nam20.news_invest.mapper.EconomicIndicatorMapper;
import com.nam20.news_invest.repository.EconomicIndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EconomicIndicatorService {

    private final EconomicIndicatorRepository economicIndicatorRepository;
    private final EconomicIndicatorMapper economicIndicatorMapper;

    public List<EconomicIndicatorResponse> retrieveFredEconomicData() {
        return economicIndicatorRepository.findAll()
                .stream()
                .map(economicIndicatorMapper::toDto)
                .toList();
    }
}
