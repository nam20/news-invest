package com.nam20.news_invest.entity.ai;

import com.nam20.news_invest.entity.Portfolio;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "investment_strategies")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvestmentStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    // 생성 시각
    @CreatedDate
    private LocalDateTime createdAt;

    // 신규 추천 종목
    @OneToMany(mappedBy = "investmentStrategy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;

    // 포트폴리오 조정안
    @OneToMany(mappedBy = "investmentStrategy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioAdjustment> portfolioAdjustments;

    // 전략적 근거
    @Lob
    private String strategicRationale;

    @Entity
    @Table(name = "investment_strategy_recommendations")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Recommendation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String ticker;
        private String name;
        private String type;
        private String reason;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "investment_strategy_id")
        private InvestmentStrategy investmentStrategy;
    }

    @Entity
    @Table(name = "investment_strategy_portfolio_adjustments")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PortfolioAdjustment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String ticker;
        private String action;
        private String reason;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "investment_strategy_id")
        private InvestmentStrategy investmentStrategy;
    }
} 