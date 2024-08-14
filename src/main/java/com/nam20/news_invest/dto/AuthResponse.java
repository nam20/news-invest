package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AuthResponse {
    private String message;
    private UserResponse user;
    private String token;

    public AuthResponse(String message, UserResponse user, String token) {
        this.message = message;
        this.user = user;
        this.token = token;
    }
}
