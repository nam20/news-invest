package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.RefreshToken;
import com.nam20.news_invest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
} 