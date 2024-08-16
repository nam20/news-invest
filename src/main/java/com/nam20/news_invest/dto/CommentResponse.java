package com.nam20.news_invest.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class CommentResponse {
    private final Long id;
    private final Long userId;
    private final Long postId;
    private final String content;
    private final String createdAt;
    private final String updatedAt;
    private final Long parentCommentId;
    private final List<CommentResponse> replies;
}
