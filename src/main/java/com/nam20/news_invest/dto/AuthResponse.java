package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthResponse {
    private UserResponse user;
    private String token;

    @Builder
    public AuthResponse(UserResponse user, String token) {
        this.user = user;
        this.token = token;
    }
}
