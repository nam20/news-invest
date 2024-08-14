package com.nam20.news_invest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UnauthorizedOwnershipException extends RuntimeException {
    public UnauthorizedOwnershipException(String message) {
        super(message);
    }
}
