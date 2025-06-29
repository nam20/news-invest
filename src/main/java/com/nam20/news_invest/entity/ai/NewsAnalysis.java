package com.nam20.news_invest.entity.ai;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news_analyses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생성 시각
    @CreatedDate
    private LocalDateTime createdAt;

    // 요약
    @ElementCollection
    private List<String> summary;

    // 감성 분석
    @Embedded
    private SentimentAnalysis sentimentAnalysis;

    // 영향받는 기업/산업
    @Embedded
    private AffectedEntities affectedEntities;

    // 종목별 영향 예측
    @OneToMany(mappedBy = "newsAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockImpactPredictions> stockImpactPredictions;

    // --- Embedded/Entity 내부 클래스들 ---

    @Embeddable
    @Getter @Setter
    public static class SentimentAnalysis {
        @Embedded
        private OverallSentiment overall;
        @ElementCollection
        private List<MentionedCompanySentiment> mentionedCompanies;
    }

    @Embeddable
    @Getter @Setter
    public static class OverallSentiment {
        private String tone;
        private String reason;
    }

    @Embeddable
    @Getter @Setter
    public static class MentionedCompanySentiment {
        private String companyName;
        private String tone;
        private String reason;
    }

    @Embeddable
    @Getter @Setter
    public static class AffectedEntities {
        @ElementCollection
        private List<String> companies;
        @ElementCollection
        private List<String> industries;
    }

    @Entity
    @Table(name = "news_analysis_stock_impact_predictions")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class StockImpactPredictions {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String companyName;
        @Embedded
        private PredictionDetail shortTerm;
        @Embedded
        private PredictionDetail longTerm;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "news_analysis_id")
        private NewsAnalysis newsAnalysis;
    }

    @Embeddable
    @Getter @Setter
    public static class PredictionDetail {
        private String prediction;
        private int confidence;
        private String reason;
    }
} 