package com.nam20.news_invest.dto.ai;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvestmentStrategyResponse {
    private List<Recommendation> recommendations;
    private List<PortfolioAdjustment> portfolioAdjustments;
    private String strategicRationale;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Recommendation {
        private String ticker;
        private String name;
        private String type;
        private String reason;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PortfolioAdjustment {
        private String ticker;
        private String action;
        private String reason;
    }
} 