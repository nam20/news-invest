package com.nam20.news_invest.dto.ai;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskAnalysisResponse {
    private OverallRiskAnalysis overallRiskAnalysis;
    private List<RiskFactor> topRiskFactors;
    private List<RiskContribution> riskContributions;
    private List<RiskMitigationPlan> riskMitigationPlans;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OverallRiskAnalysis {
        private int score;
        private String assessment;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RiskFactor {
        private String factor;
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RiskContribution {
        private String ticker;
        private String contribution;
        private String reason;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RiskMitigationPlan {
        private String action;
        private String details;
    }
} 