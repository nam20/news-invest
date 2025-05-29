package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.LoginRequest;
import com.nam20.news_invest.dto.RegisterRequest;
import com.nam20.news_invest.dto.UserResponse;
import com.nam20.news_invest.entity.RefreshToken;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.TokenRefreshException;
import com.nam20.news_invest.mapper.UserMapper;
import com.nam20.news_invest.security.JwtGenerator;
import com.nam20.news_invest.service.RefreshTokenService;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = userService.createUser(registerRequest);
        // 회원가입 후 자동 로그인 및 토큰 발급
        return authenticateAndGenerateResponse(
                registerRequest.getName(), registerRequest.getPassword(), userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticateAndGenerateResponse(
                loginRequest.getName(), loginRequest.getPassword(), null);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        // 액세스 토큰 쿠키 무효화
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        // 리프레시 토큰 쿠키 무효화
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        // 저장소에서 리프레시 토큰 삭제
        if (refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken);
        }

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                 .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    @GetMapping("/check")
    public ResponseEntity<UserResponse> refresh(@AuthenticationPrincipal User user) {
        if (user != null) {
            return ResponseEntity.ok(userMapper.toDto(user));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // 토큰 갱신 엔드포인트
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("리프레시 토큰이 제공되지 않았습니다.");
        }

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // 새 액세스 토큰 생성
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                     SecurityContextHolder.getContext().setAuthentication(authentication);

                    String newAccessToken = jwtGenerator.generateToken(authentication);

                    // 새 액세스 토큰 쿠키 생성
                    ResponseCookie newAccessCookie = ResponseCookie.from("accessToken", newAccessToken)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(JwtGenerator.getAccessTokenExpiration())
                            .sameSite("Strict")
                            .build();

                    // 필요에 따라 rolling refresh token 구현:
                    // 1. 기존 리프레시 토큰 삭제
                    // refreshTokenService.deleteByToken(refreshToken);
                    // 2. 새 리프레시 토큰 생성 및 저장
                    // RefreshToken newRefreshTokenEntity = refreshTokenService.generateRefreshToken(user.getId());
                    // String newRefreshToken = newRefreshTokenEntity.getToken();
                    // 3. 새 리프레시 토큰 쿠키 생성하여 응답에 포함

                    UserResponse userResponse = userMapper.toDto(user);

                    return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, newAccessCookie.toString())
                            // .header(HttpHeaders.SET_COOKIE, newRefreshCookie.toString())
                            .body(userResponse);
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "유효하지 않은 리프레시 토큰입니다. 다시 로그인해주세요."));
    }


    private ResponseEntity<UserResponse> authenticateAndGenerateResponse(
            String username, String password, UserResponse preCreatedUserResponse
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        // 액세스 토큰 생성
        String accessToken = jwtGenerator.generateToken(authentication);

        // 리프레시 토큰 생성 및 저장
        RefreshToken refreshTokenEntity = refreshTokenService.generateRefreshToken(user.getId());
        String refreshToken = refreshTokenEntity.getToken();

        // 액세스 토큰 쿠키 생성
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(JwtGenerator.getAccessTokenExpiration())
                .sameSite("Strict")
                .build();

        // 리프레시 토큰 쿠키 생성
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                 .maxAge(JwtGenerator.getRefreshTokenExpiration()) // 리프레시 토큰 만료 시간 (초 단위)
                .sameSite("Strict")
                .build();

        UserResponse userResponse;

        if (preCreatedUserResponse == null) {
            userResponse = userMapper.toDto(user);
        } else {
            userResponse = preCreatedUserResponse;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(userResponse);
    }
}
