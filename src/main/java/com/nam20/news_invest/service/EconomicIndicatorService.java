package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.EconomicIndicatorResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.EconomicIndicator;
import com.nam20.news_invest.mapper.EconomicIndicatorMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.EconomicIndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EconomicIndicatorService {

    private final EconomicIndicatorRepository economicIndicatorRepository;
    private final EconomicIndicatorMapper economicIndicatorMapper;
    private final PaginationMetaMapper paginationMetaMapper;

    public PaginationResponse<EconomicIndicatorResponse> retrieveFredEconomicData(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<EconomicIndicator> economicIndicatorsPage = economicIndicatorRepository.findAll(pageable);

        List<EconomicIndicatorResponse> economicIndicatorResponses = economicIndicatorsPage
                .getContent()
                .stream()
                .map(economicIndicatorMapper::toDto)
                .toList();

        return new PaginationResponse<>(economicIndicatorResponses,
                paginationMetaMapper.toPaginationMeta(economicIndicatorsPage));
    }
}
