package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
