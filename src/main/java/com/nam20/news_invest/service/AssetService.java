package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.AssetResponse;
import com.nam20.news_invest.entity.Asset;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.AssetMapper;
import com.nam20.news_invest.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public List<AssetResponse> retrieveAssets(Long portfolioId) {
        return assetRepository.findByPortfolioId(portfolioId)
                .stream()
                .map(assetMapper::toDto)
                .toList();
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
