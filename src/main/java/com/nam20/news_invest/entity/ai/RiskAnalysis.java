package com.nam20.news_invest.entity.ai;

import com.nam20.news_invest.entity.Portfolio;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "risk_analyses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    // 생성 시각
    @CreatedDate
    private LocalDateTime createdAt;

    // 전체 리스크 평가
    @Embedded
    private OverallRiskAnalysis overallRiskAnalysis;

    // 주요 리스크 요인
    @OneToMany(mappedBy = "riskAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RiskFactor> riskFactors;

    // 종목별 리스크 기여도
    @OneToMany(mappedBy = "riskAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RiskContribution> riskContributions;

    // 리스크 완화 방안
    @OneToMany(mappedBy = "riskAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RiskMitigationPlan> riskMitigationPlans;

    @Embeddable
    @Getter @Setter
    public static class OverallRiskAnalysis {
        private int score;
        private String assessment;
    }

    @Entity
    @Table(name = "risk_analysis_risk_factors")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RiskFactor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String factor;
        private String description;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "risk_analysis_id")
        private RiskAnalysis riskAnalysis;
    }

    @Entity
    @Table(name = "risk_analysis_risk_contributions")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RiskContribution {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String ticker;
        private String contribution;
        private String reason;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "risk_analysis_id")
        private RiskAnalysis riskAnalysis;
    }

    @Entity
    @Table(name = "risk_analysis_risk_mitigation_plans")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RiskMitigationPlan {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String action;
        private String details;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "risk_analysis_id")
        private RiskAnalysis riskAnalysis;
    }
} 