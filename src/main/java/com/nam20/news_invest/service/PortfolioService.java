package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.dto.PortfolioRequest;
import com.nam20.news_invest.dto.PortfolioResponse;
import com.nam20.news_invest.entity.Portfolio;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.mapper.PortfolioMapper;
import com.nam20.news_invest.repository.PortfolioRepository;
import com.nam20.news_invest.service.ai.InvestmentStrategyService;
import com.nam20.news_invest.service.ai.RiskAnalysisService;
import com.nam20.news_invest.entity.ai.InvestmentStrategy;
import com.nam20.news_invest.entity.ai.RiskAnalysis;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final PaginationMetaMapper paginationMetaMapper;
    private final InvestmentStrategyService investmentStrategyService;
    private final RiskAnalysisService riskAnalysisService;

    @Cacheable(value = "portfolios", key = "#user.id + '_' + #page + '_' + #size")
    public PaginationResponse<PortfolioResponse> retrievePortfolios(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Portfolio> portfoliosPage = portfolioRepository.findByUserId(user.getId(), pageable);

        List<PortfolioResponse> portfolioResponses = portfoliosPage
                .getContent()
                .stream()
                .map(portfolioMapper::toDto)
                .toList();

        return new PaginationResponse<>(portfolioResponses,
                paginationMetaMapper.toPaginationMeta(portfoliosPage));
    }

    @Cacheable(value = "portfolios", key = "#id")
    public PortfolioResponse retrievePortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        return portfolioMapper.toDto(portfolio);
    }

    @Transactional
    public PortfolioResponse createPortfolio(PortfolioRequest portfolioRequest) {
        Portfolio portfolio = Portfolio.builder()
                .name(portfolioRequest.getName())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        return portfolioMapper.toDto(savedPortfolio);
    }

    @CachePut(value = "portfolios", key = "#id")
    public PortfolioResponse updatePortfolio(Long id, PortfolioRequest portfolioRequest) {
        Portfolio existingPortfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        existingPortfolio.updateName(portfolioRequest.getName());

        Portfolio updatedPortfolio = portfolioRepository.save(existingPortfolio);

        return portfolioMapper.toDto(updatedPortfolio);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        portfolioRepository.deleteById(id);
    }

    /**
     * 주어진 포트폴리오 ID와 프롬프트 변수로 AI 투자 전략을 생성 및 저장
     * @param portfolioId 분석할 포트폴리오 ID
     * @param variables 프롬프트에 사용할 변수 맵
     * @return 생성된 InvestmentStrategy 엔티티
     */
    public InvestmentStrategy analyzeInvestmentStrategy(Long portfolioId, Map<String, String> variables) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("id " + portfolioId));

        return investmentStrategyService.analyzeAndSave(variables, portfolio);
    }

    /**
     * 주어진 포트폴리오 ID와 프롬프트 변수로 AI 리스크 분석을 생성 및 저장
     * @param portfolioId 분석할 포트폴리오 ID
     * @param variables 프롬프트에 사용할 변수 맵
     * @return 생성된 RiskAnalysis 엔티티
     */
    public RiskAnalysis analyzeRiskAnalysis(Long portfolioId, Map<String, String> variables) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("id " + portfolioId));
                
        return riskAnalysisService.analyzeAndSave(variables, portfolio);
    }
}
