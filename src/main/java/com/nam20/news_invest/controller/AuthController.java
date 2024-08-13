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
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterDto registerDto) {

        UserDto userDto = userService.createUser(registerDto);
        String token = retrieveToken(registerDto.getName(), registerDto.getPassword());

        AuthResponseDto responseDto = new AuthResponseDto(
                "User registered successfully",
                userDto, token
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {

        String token = retrieveToken(loginDto.getName(), loginDto.getPassword());
        UserDto userDto = userService.retrieveUserByName(loginDto.getName());

        AuthResponseDto responseDto = new AuthResponseDto(
                "User logged in successfully",
                userDto, token
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
