package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String content;
    private String category;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
