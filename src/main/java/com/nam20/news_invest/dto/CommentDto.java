package com.nam20.news_invest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CommentDto {

    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private String createdAt;
    private String updatedAt;
    private Long parentCommentId;
    private List<CommentDto> replies;

    @Builder
    public CommentDto(Long id, Long userId, Long postId, String content, String createdAt,
                      String updatedAt, Long parentCommentId, List<CommentDto> replies) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.parentCommentId = parentCommentId;
        this.replies = replies;
    }
}
