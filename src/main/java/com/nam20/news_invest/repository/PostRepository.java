package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
