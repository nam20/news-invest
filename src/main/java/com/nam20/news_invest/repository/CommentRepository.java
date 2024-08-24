package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByUserId(Long userId, Pageable pageable);
}
