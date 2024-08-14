package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.*;
import com.nam20.news_invest.repository.UserRepository;
import com.nam20.news_invest.security.JwtGenerator;
import com.nam20.news_invest.service.UserService;
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

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {

        UserResponse userResponse = userService.createUser(registerRequest);
        String token = retrieveToken(registerRequest.getName(), registerRequest.getPassword());

        AuthResponse responseDto = new AuthResponse(
                "User registered successfully",
                userResponse, token
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        String token = retrieveToken(loginRequest.getName(), loginRequest.getPassword());
        UserResponse userResponse = userService.retrieveUserByName(loginRequest.getName());

        AuthResponse responseDto = new AuthResponse(
                "User logged in successfully",
                userResponse, token
        );

        return ResponseEntity.ok(responseDto);
    }

    private String retrieveToken(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenerator.generateToken(authentication);
    }
}
