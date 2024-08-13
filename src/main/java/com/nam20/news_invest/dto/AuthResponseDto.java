package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AuthResponseDto {
    private String message;
    private UserDto user;
    private String token;

    public AuthResponseDto(String message, UserDto user, String token) {
        this.message = message;
        this.user = user;
        this.token = token;
    }
}
