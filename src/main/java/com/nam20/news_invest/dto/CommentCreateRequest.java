package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class CommentCreateRequest {
    private final String content;
    private final Long postId;
    private final Long parentCommentId;
}
