package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostDto {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> comments;

    @Builder
    public PostDto(Long id, Long userId, String title, String content, String category,
                   LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentDto> comments) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
    }
}
