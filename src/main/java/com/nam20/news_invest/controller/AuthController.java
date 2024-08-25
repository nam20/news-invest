package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.AuthResponse;
import com.nam20.news_invest.dto.LoginRequest;
import com.nam20.news_invest.dto.RegisterRequest;
import com.nam20.news_invest.dto.UserResponse;
import com.nam20.news_invest.security.JwtGenerator;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        UserResponse userResponse = userService.createUser(registerRequest);
        String token = retrieveToken(registerRequest.getName(), registerRequest.getPassword());

        AuthResponse authResponse = AuthResponse.builder()
                .user(userResponse)
                .token(token)
                .build();

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        String token = retrieveToken(loginRequest.getName(), loginRequest.getPassword());
        UserResponse userResponse = userService.retrieveUserByName(loginRequest.getName());

        AuthResponse authResponse = AuthResponse.builder()
                .user(userResponse)
                .token(token)
                .build();

        return ResponseEntity.ok(authResponse);
    }

    private String retrieveToken(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenerator.generateToken(authentication);
    }
}
