package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostResponse {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final String category;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<CommentResponse> comments;
}
