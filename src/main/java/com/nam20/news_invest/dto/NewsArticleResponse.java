package com.nam20.news_invest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class NewsArticleResponse {
    private final Long id;
    private final String title;
    private final String url;
    private final String description;
    private final String thumbnailUrl;
    private final String provider;
    private final LocalDateTime publishedAt;
    private final String content;
}
