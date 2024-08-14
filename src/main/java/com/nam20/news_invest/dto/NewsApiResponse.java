package com.nam20.news_invest.dto;

import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NewsApiResponse {
    private final String status;
    private final int totalResults;
    private final List<NewsApiResponseArticle> articles;
}