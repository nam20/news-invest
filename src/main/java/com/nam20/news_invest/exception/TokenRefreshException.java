package com.nam20.news_invest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

    private final String token;
    private final String message;

    public TokenRefreshException(String token, String message) {
        super(String.format("토큰 [%s]: %s", token, message));
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getMessage() {
        return message;
    }
} 