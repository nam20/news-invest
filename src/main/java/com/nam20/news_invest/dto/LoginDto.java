package com.nam20.news_invest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LoginDto {
    private String name;
    private String password;
}