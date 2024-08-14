package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class NewsApiResponseArticle {
    private final String author;
    private final String title;
    private final String description;
    private final String url;
    private final String urlToImage;
    private final LocalDate publishedAt;
    private final String content;
}
