package com.nam20.news_invest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
