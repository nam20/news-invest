package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class News {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String url;
    private String urlToImage;

    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDate publishedAt;

    @Builder
    public News(Long id, String author, String title, String description, String url,
                String urlToImage, String content, LocalDate publishedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
        this.publishedAt = publishedAt;
    }
}
