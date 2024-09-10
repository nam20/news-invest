package com.nam20.news_invest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private String createdAt;
    private String updatedAt;
    private Long parentCommentId;
    private Integer depth;
}
