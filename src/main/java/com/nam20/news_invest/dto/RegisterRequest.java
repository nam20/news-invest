package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
