package com.nam20.news_invest.dto.ai;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsAnalysisResponse {
    private List<String> summary;
    private SentimentAnalysis sentimentAnalysis;
    private AffectedEntities affectedEntities;
    private List<StockImpactPrediction> stockImpactPredictions;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SentimentAnalysis {
        private OverallSentiment overall;
        private List<MentionedCompanySentiment> mentionedCompanies;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OverallSentiment {
        private String tone;
        private String reason;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MentionedCompanySentiment {
        private String companyName;
        private String tone;
        private String reason;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AffectedEntities {
        private List<String> companies;
        private List<String> industries;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class StockImpactPrediction {
        private String companyName;
        private PredictionDetail shortTerm;
        private PredictionDetail longTerm;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PredictionDetail {
        private String prediction;
        private int confidence;
        private String reason;
    }
} 