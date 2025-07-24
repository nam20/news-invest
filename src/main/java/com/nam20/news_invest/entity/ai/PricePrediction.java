package com.nam20.news_invest.entity.ai;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "price_predictions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PricePrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 생성 시각
    @CreatedDate
    private LocalDateTime createdAt;

    // 예측 정보
    @Embedded
    private Prediction prediction;

    // 예측에 영향을 준 주요 근거 리스트
    @OneToMany(mappedBy = "pricePrediction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PredictionRationale> rationales;

    // 참고/주의사항 리스트
    @OneToMany(mappedBy = "pricePrediction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PredictionNote> notes;

    // 예측 정보(내장 클래스)
    @Embeddable
    @Getter @Setter
    public static class Prediction {
        private String direction; // 예측 방향: 상승|하락|중립
        private double changePercent; // 예상 변동폭(%)
        private int confidence; // 신뢰도(1~10)
    }

    // 예측 근거 엔티티
    @Entity
    @Table(name = "price_prediction_rationales")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PredictionRationale {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String rationale; // 근거 내용
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "price_prediction_id")
        private PricePrediction pricePrediction;
    }

    // 참고/주의사항 엔티티
    @Entity
    @Table(name = "price_prediction_notes")
    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PredictionNote {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String note; // 참고/주의사항 내용
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "price_prediction_id")
        private PricePrediction pricePrediction;
    }
} 