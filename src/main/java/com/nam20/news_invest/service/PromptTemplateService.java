package com.nam20.news_invest.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptTemplateService {

    /**
     * 뉴스 분석을 위한 Gemini 프롬프트 템플릿을 생성합니다.
     *
     * @param newsArticleContent   분석할 뉴스 기사 본문
     * @param userInterestedStocks 사용자의 관심 종목 리스트
     * @return 생성된 프롬프트 문자열
     */
    public String createNewsAnalysisPrompt(String newsArticleContent, List<String> userInterestedStocks) {
        String stocks = userInterestedStocks == null || userInterestedStocks.isEmpty() ? "없음" :
                String.join(", ", userInterestedStocks);

        return """
                # PERSONA
                당신은 AI 금융 분석가입니다. 당신의 임무는 뉴스 기사를 분석하여 개인 투자자에게 명확하고 실행 가능한 인사이트를 제공하는 것입니다. 감성적인 판단보다는 데이터와 사실에 기반하여 분석해야 합니다.

                # CONTEXT
                - 분석할 뉴스 기사:
                ""\"
                %s
                ""\"
                - 사용자의 관심 종목 리스트: [%s]

                # TASK
                위 컨텍스트를 바탕으로 다음 작업을 수행해 주세요:
                1.  **핵심 요약**: 기사의 핵심 내용을 3개의 불릿 포인트로 요약하세요.
                2.  **감성 분석**: 기사가 시장 전반 및 언급된 개별 기업에 대해 가지는 긍정/중립/부정 톤을 분석하고, 그 근거를 기사 내용에서 찾아 제시하세요.
                3.  **주요 영향 기업 및 산업**: 기사 내용에 직접적으로 언급되거나 간접적으로 영향을 받을 것으로 예상되는 기업 및 산업 분야를 모두 식별하세요.
                4.  **주가 영향 예측**: 위에서 식별된 기업들의 주가에 미칠 단기(1주일) 및 장기(6개월) 전망을 '상승', '하락', '중립'으로 예측하고, 각 예측에 대한 신뢰도 점수(1~10점)와 그 이유를 설명하세요.

                # FORMAT
                결과는 반드시 다음 JSON 형식에 맞춰 한국어로 작성해 주세요. 어떠한 설명도 JSON 객체 외부에 추가하지 마세요.

                {
                  "summary": [
                    "요약1",
                    "요약2",
                    "요약3"
                  ],
                  "sentimentAnalysis": {
                    "overall": {
                      "tone": "긍정|중립|부정",
                      "reason": "분석 근거"
                    },
                    "mentionedCompanies": [
                      {
                        "companyName": "기업명",
                        "tone": "긍정|중립|부정",
                        "reason": "분석 근거"
                      }
                    ]
                  },
                  "affectedEntities": {
                    "companies": ["기업명1", "기업명2"],
                    "industries": ["산업명1", "산업명2"]
                  },
                  "stockImpactPrediction": [
                    {
                      "companyName": "기업명",
                      "shortTerm": {
                        "prediction": "상승|하락|중립",
                        "confidence": 8,
                        "reason": "단기 예측 근거"
                      },
                      "longTerm": {
                        "prediction": "상승|하락|중립",
                        "confidence": 7,
                        "reason": "장기 예측 근거"
                      }
                    }
                  ]
                }
                """.formatted(newsArticleContent, stocks);
    }

    /**
     * 투자 전략 제안을 위한 Gemini 프롬프트 템플릿을 생성합니다.
     *
     * @param userRiskTolerance     사용자 투자 성향
     * @param userInvestmentHorizon 사용자 투자 기간
     * @param userPortfolioDetails  사용자 포트폴리오 상세 (JSON 문자열 또는 포맷된 문자열)
     * @param totalPortfolioValue   총 포트폴리오 가치
     * @param marketNewsSummary     주요 시장 뉴스 요약
     * @param keyEconomicIndicators 주요 경제 지표
     * @return 생성된 프롬프트 문자열
     */
    public String createInvestmentStrategyPrompt(String userRiskTolerance, String userInvestmentHorizon,
                                                 String userPortfolioDetails, String totalPortfolioValue,
                                                 String marketNewsSummary, String keyEconomicIndicators) {
        return """
                # PERSONA
                당신은 AI 투자 자문가입니다. 당신의 목표는 사용자의 재정 상황과 목표에 맞춰 안정적이면서도 수익성 있는 투자 전략을 제안하는 것입니다.

                # CONTEXT
                - **사용자 프로필**:
                  - 투자 성향: %s
                  - 투자 기간: %s
                - **현재 포트폴리오**:
                  - 보유 종목: %s
                  - 총 평가액: %s
                - **최신 시장 데이터**:
                  - 주요 뉴스 요약: %s
                  - 주요 경제 지표: %s

                # TASK
                주어진 컨텍스트를 바탕으로 다음 작업을 수행해 주세요:
                1.  **신규 투자 추천**: 사용자의 프로필과 현재 시장 상황에 가장 적합한 신규 투자처(주식, ETF, 채권 등) 3개를 추천하고, 각각의 추천 이유를 상세히 설명하세요.
                2.  **포트폴리오 조정 제안**: 현재 포트폴리오의 리밸런싱 전략을 제안하세요. 비중을 축소할 종목과 확대할 종목을 구체적으로 제시하고, 그 이유를 논리적으로 설명하세요.
                3.  **전략적 근거**: 위 모든 제안의 바탕이 된 전반적인 시장 분석과 전략적 판단 근거를 요약하여 설명하세요.

                # FORMAT
                결과는 반드시 다음 JSON 형식에 맞춰 한국어로 작성해 주세요.

                {
                  "newRecommendations": [
                    {
                      "ticker": "추천 종목 코드",
                      "name": "추천 종목명",
                      "type": "주식|ETF|채권",
                      "reason": "추천 이유 상세 설명"
                    }
                  ],
                  "portfolioAdjustments": [
                    {
                      "ticker": "조정 대상 종목 코드",
                      "action": "비중 확대|비중 축소|매도",
                      "reason": "조정 제안 이유"
                    }
                  ],
                  "strategicRationale": "전반적인 시장 분석 및 전략적 판단 요약"
                }
                """.formatted(userRiskTolerance, userInvestmentHorizon, userPortfolioDetails, totalPortfolioValue, marketNewsSummary, keyEconomicIndicators);
    }

    /**
     * 포트폴리오 리스크 분석을 위한 Gemini 프롬프트 템플릿을 생성합니다.
     *
     * @param userPortfolioDetails  사용자 포트폴리오 상세 (JSON 문자열 또는 포맷된 문자열)
     * @param relevantNegativeNews  관련 부정적 뉴스
     * @param vixIndex              시장 변동성 지수
     * @return 생성된 프롬프트 문자열
     */
    public String createRiskAnalysisPrompt(String userPortfolioDetails, String relevantNegativeNews, String vixIndex) {
        return """
                # PERSONA
                당신은 AI 퀀트 리스크 분석가입니다. 당신의 임무는 포트폴리오의 잠재적 위험을 체계적으로 분석하고, 데이터를 기반으로 한 리스크 관리 방안을 제시하는 것입니다.

                # CONTEXT
                - **사용자 포트폴리오**: %s
                - **포트폴리오 관련 최신 부정적 뉴스**: %s
                - **현재 시장 변동성 지수(VIX)**: %s

                # TASK
                주어진 컨텍스트를 바탕으로 다음 작업을 수행해 주세요:
                1.  **종합 리스크 진단**: 현재 포트폴리오의 종합 리스크 점수를 100점 만점으로 평가하고, 점수에 대한 상세한 설명을 덧붙이세요. (점수가 낮을수록 리스크가 높음)
                2.  **핵심 리스크 요인 식별**: 이 포트폴리오가 직면한 가장 중요한 리스크 요인 3가지를 식별하세요. (예: 시장 리스크, 특정 산업 쏠림 리스크, 개별 종목의 악재 등)
                3.  **리스크 기여도 분석**: 포트폴리오 내 각 자산이 전체 리스크에 얼마나 기여하는지 분석하여 상위 3개 종목을 나열하세요.
                4.  **리스크 관리 방안**: 식별된 리스크들을 완화하기 위한 구체적인 실행 방안 3가지를 제안하세요. (예: 특정 자산 비중 축소, 분산 투자 강화, 헷지 전략 등)

                # FORMAT
                결과는 반드시 다음 JSON 형식에 맞춰 한국어로 작성해 주세요.

                {
                  "overallRiskAnalysis": {
                    "score": 75,
                    "assessment": "종합 리스크 점수에 대한 상세 평가"
                  },
                  "topRiskFactors": [
                    {
                      "factor": "리스크 요인 1",
                      "description": "해당 리스크에 대한 설명"
                    },
                    {
                      "factor": "리스크 요인 2",
                      "description": "해당 리스크에 대한 설명"
                    }
                  ],
                  "riskContribution": [
                      {"ticker": "종목코드1", "contribution": "높음", "reason": "기여도가 높은 이유"},
                      {"ticker": "종목코드2", "contribution": "중간", "reason": "기여도가 중간인 이유"}
                  ],
                  "riskMitigationPlan": [
                    {
                      "action": "실행 방안 1",
                      "details": "실행 방안에 대한 구체적인 설명"
                    },
                    {
                      "action": "실행 방안 2",
                      "details": "실행 방안에 대한 구체적인 설명"
                    }
                  ]
                }
                """.formatted(userPortfolioDetails, relevantNegativeNews, vixIndex);
    }
} 