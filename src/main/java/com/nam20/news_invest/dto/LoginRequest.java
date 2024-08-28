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
            message = "Name must be 4 to 20 characters long, containing only alphabets and numbers."
    )
    @NotBlank(message = "Name is required")
    private final String name;

    @Pattern(
            regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$",
            message = "Password must be 8 to 20 characters long, containing at least one lowercase letter, one number, and one special character (#?!@$%^&*-)."
    )
    @NotBlank(message = "Password is required")
    private final String password;
}
