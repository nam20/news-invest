package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Boolean existsByName(String name);
    Boolean existsByEmail(String email);
}
