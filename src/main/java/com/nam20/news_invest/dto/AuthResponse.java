package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    private final String message;
    private final UserResponse user;
    private final String token;
}
