package com.nam20.news_invest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class NewsApiResponse {

    private String status;
    private int totalResults;
    private List<NewsApiResponseArticle> articles;

    @Builder
    public NewsApiResponse(String status, int totalResults, List<NewsApiResponseArticle> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }
}