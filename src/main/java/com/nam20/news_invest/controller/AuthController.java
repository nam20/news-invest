package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.AuthResponse;
import com.nam20.news_invest.dto.LoginRequest;
import com.nam20.news_invest.dto.RegisterRequest;
import com.nam20.news_invest.dto.UserResponse;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.mapper.UserMapper;
import com.nam20.news_invest.security.JwtGenerator;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;
    private final UserMapper userMapper;
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = userService.createUser(registerRequest);
        return authenticateAndGenerateResponse(
                registerRequest.getName(), registerRequest.getPassword(), userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticateAndGenerateResponse(
                loginRequest.getName(), loginRequest.getPassword(), null);
    }

    private ResponseEntity<AuthResponse> authenticateAndGenerateResponse(
            String username, String password, UserResponse preCreatedUserResponse
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(COOKIE_MAX_AGE)
                .sameSite("Strict")
                .build();

        UserResponse userResponse;

        if (preCreatedUserResponse == null) {
            User user = (User) authentication.getPrincipal();
            userResponse = userMapper.toDto(user);
        } else {
            userResponse = preCreatedUserResponse;
        }

        AuthResponse authResponse = AuthResponse.builder()
                .user(userResponse)
                .token(token)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResponse);
    }
}
