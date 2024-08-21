package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_articles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailUrl;

    private String provider;

    private LocalDateTime publishedAt;

    @Builder
    public NewsArticle(String title, String url, String description, String thumbnailUrl,
                       String provider, LocalDateTime publishedAt) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.provider = provider;
        this.publishedAt = publishedAt;
    }
}
