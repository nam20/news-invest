package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.PortfolioRequest;
import com.nam20.news_invest.dto.PortfolioResponse;
import com.nam20.news_invest.entity.Portfolio;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.PortfolioMapper;
import com.nam20.news_invest.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    public List<PortfolioResponse> retrievePortfolios(User user) {
        return portfolioRepository.findByUserId(user.getId())
                .stream()
                .map(portfolioMapper::toDto)
                .toList();
    }

    public PortfolioResponse retrievePortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        return portfolioMapper.toDto(portfolio);
    }

    public PortfolioResponse createPortfolio(PortfolioRequest portfolioRequest) {
        Portfolio portfolio = Portfolio.builder()
                .name(portfolioRequest.getName())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        return portfolioMapper.toDto(savedPortfolio);
    }

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
}
