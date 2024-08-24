package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.AssetResponse;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.Asset;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.AssetMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;
    private final PaginationMetaMapper paginationMetaMapper;

    public PaginationResponse<AssetResponse> retrieveAssets(Long portfolioId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Asset> assetsPage = assetRepository.findByPortfolioId(portfolioId, pageable);

        List<AssetResponse> assetResponses = assetsPage
                .getContent()
                .stream()
                .map(assetMapper::toDto)
                .toList();

        return new PaginationResponse<>(assetResponses,
                paginationMetaMapper.toPaginationMeta(assetsPage));
    }

    public AssetResponse retrieveAsset(Long portfolioId, Long assetId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + assetId));

        return assetMapper.toDto(asset);
    }

    public void deleteAsset(Long portfolioId, Long assetId) {
        assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + assetId));

        assetRepository.deleteById(assetId);
    }
}
