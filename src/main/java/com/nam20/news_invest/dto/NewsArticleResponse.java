package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsArticleResponse {
    private Long id;
    private String title;
    private String url;
    private String description;
    private String thumbnailUrl;
    private String provider;
    private LocalDateTime publishedAt;
    private String content;
}
