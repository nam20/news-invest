package com.nam20.news_invest.util;

import org.springframework.stereotype.Component;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.nam20.news_invest.exception.ExternalApiException;

@Component
public class GeminiApiClient {
    // 환경 변수에 GOOGLE_API_KEY가 설정되어 있으면 Client가 자동으로 인식
    private final Client client = new Client();

    public String generateContent(String prompt) {
        try {
            GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
            );
            return response.text();
        } catch (Exception e) {
            throw new ExternalApiException("Gemini API 호출 실패", e);
        }
        
    }
} 