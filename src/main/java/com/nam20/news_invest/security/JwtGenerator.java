package com.nam20.news_invest.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtGenerator {

    private static final int ACCESS_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    private static final int REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    private final SecretKey secretKey;

    public JwtGenerator(@Value("${jwt.secret}") String jwtSecret) {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + ACCESS_TOKEN_EXPIRATION);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts
                .builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT 토큰이 만료되었습니다.", ex);
        } catch (MalformedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT 토큰이 올바르지 않은 형식입니다.", ex);
        } catch (UnsupportedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("지원하지 않는 JWT 토큰입니다.", ex);
        } catch (SignatureException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT 토큰의 서명이 유효하지 않습니다.", ex);
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT 토큰 검증 중 오류가 발생했습니다: " + ex.getMessage(), ex);
        }
    }

    public List<String> getRolesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token).getPayload();

        return claims.get("roles", List.class);
    }

    public static int getRefreshTokenExpiration() {
        return REFRESH_TOKEN_EXPIRATION / 1000;
    }

    public static int getAccessTokenExpiration() {
        return ACCESS_TOKEN_EXPIRATION / 1000;
    }
}
