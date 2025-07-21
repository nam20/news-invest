package com.nam20.news_invest.dto.ai;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PricePredictionResponse {
    private Prediction prediction;
    private List<String> rationale; // 예측에 영향을 준 주요 근거 리스트
    private List<String> notes; // 참고/주의사항 리스트

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Prediction {
        private String direction; // 예측 방향: 상승|하락|중립
        private double changePercent; // 예상 변동폭(%)
        private int confidence; // 신뢰도(1~10)
    }
} 