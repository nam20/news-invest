package com.nam20.news_invest.dto;

import java.util.List;

public class NewsApiResponse {
    private String status;
    private int totalResults;
    private List<NewsApiResponseArticle> articles;

    public NewsApiResponse(String status, int totalResults, List<NewsApiResponseArticle> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public NewsApiResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsApiResponseArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsApiResponseArticle> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "NewsApiResponseDTO{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}