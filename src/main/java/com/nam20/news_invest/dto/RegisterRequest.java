package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class RegisterRequest {
    private final String name;
    private final String email;
    private final String password;
}
