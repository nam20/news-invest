package com.nam20.news_invest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class LoginRequest {

    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,20}$",
            message = "{authRequest.name.pattern}"
    )
    @NotBlank(message = "{authRequest.name.required}")
    private final String name;

    @Pattern(
            regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$",
            message = "{authRequest.password.pattern}"
    )
    @NotBlank(message = "{authRequest.password.required}")
    private final String password;
}
