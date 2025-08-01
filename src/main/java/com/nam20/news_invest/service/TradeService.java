package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.TradeRequest;
import com.nam20.news_invest.dto.TradeResponse;
import com.nam20.news_invest.entity.Asset;
import com.nam20.news_invest.entity.Portfolio;
import com.nam20.news_invest.entity.Trade;
import com.nam20.news_invest.enums.AssetType;
import com.nam20.news_invest.enums.TransactionType;
import com.nam20.news_invest.exception.InsufficientBalanceException;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.TradeMapper;
import com.nam20.news_invest.repository.AssetRepository;
import com.nam20.news_invest.repository.PortfolioRepository;
import com.nam20.news_invest.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;
    private final TradeMapper tradeMapper;

    @Transactional
    public TradeResponse buyAsset(Long portfolioId, TradeRequest tradeRequest) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("id " + portfolioId));

        String tradeSymbol = tradeRequest.getSymbol();
        AssetType tradeAssetType = tradeRequest.getAssetType();
        Double tradePrice = tradeRequest.getPrice();
        Double tradeQuantity = tradeRequest.getQuantity();

        Optional<Asset> optionalAsset = assetRepository.findByPortfolioIdAndSymbolAndType(
                portfolioId, tradeSymbol, tradeAssetType);

        Trade.TradeBuilder tradeBuilder = Trade.builder()
                .symbol(tradeSymbol)
                .assetType(tradeAssetType)
                .price(tradePrice)
                .quantity(tradeQuantity)
                .transactionType(TransactionType.BUY);

        if (optionalAsset.isPresent()) {
            Asset existingAsset = optionalAsset.get();

            Double existingQuantity = existingAsset.getQuantity();
            Double existingAveragePurchasePrice = existingAsset.getAveragePurchasePrice();

            double totalQuantity = existingQuantity + tradeQuantity;

            Double averagePurchasePrice =
                    ((existingAveragePurchasePrice * existingQuantity) + (tradePrice * tradeQuantity))
                            / totalQuantity;

            existingAsset.updateAveragePurchasePriceAndQuantity(averagePurchasePrice, totalQuantity);

            assetRepository.save(existingAsset);

            tradeBuilder.asset(existingAsset);
        } else {
            Asset newAsset = Asset.builder()
                    .symbol(tradeSymbol)
                    .type(tradeAssetType)
                    .averagePurchasePrice(tradePrice)
                    .quantity(tradeQuantity)
                    .lastPurchasedAt(LocalDateTime.now())
                    .portfolio(portfolio)
                    .build();

            assetRepository.save(newAsset);

            tradeBuilder.asset(newAsset);
        }

        Trade savedTrade = tradeRepository.save(tradeBuilder.build());

        return tradeMapper.toDto(savedTrade);
    }

    @Transactional
    public TradeResponse sellAsset(Long portfolioId, TradeRequest tradeRequest) {
        String tradeSymbol = tradeRequest.getSymbol();
        AssetType tradeAssetType = tradeRequest.getAssetType();
        Double tradePrice = tradeRequest.getPrice();
        Double tradeQuantity = tradeRequest.getQuantity();

        Asset asset = assetRepository.findByPortfolioIdAndSymbolAndType(
                portfolioId, tradeSymbol, tradeAssetType)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "portfolioId: " + portfolioId + 
                        ", tradeSymbol: " + tradeSymbol + 
                        ", tradeAssetType: " + tradeAssetType));

        Double existingQuantity = asset.getQuantity();
        Double existingAveragePurchasePrice = asset.getAveragePurchasePrice();

        double totalQuantity = existingQuantity - tradeQuantity;

        if (totalQuantity < 0) {
            throw new InsufficientBalanceException("판매 가능한 수량이 부족합니다.");
        }

        Double averagePurchasePrice =
                ((existingAveragePurchasePrice * existingQuantity) - (tradePrice * tradeQuantity))
                / totalQuantity;

        Trade trade = Trade.builder()
                .symbol(tradeSymbol)
                .assetType(tradeAssetType)
                .price(tradePrice)
                .quantity(tradeQuantity)
                .transactionType(TransactionType.SELL)
                .asset(asset)
                .build();

        asset.updateAveragePurchasePriceAndQuantity(averagePurchasePrice, totalQuantity);

        assetRepository.save(asset);

        Trade savedTrade = tradeRepository.save(trade);

        return tradeMapper.toDto(savedTrade);
    }
}
