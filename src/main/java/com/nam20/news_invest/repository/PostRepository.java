package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"user"})
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByUserId(Long userId, Pageable pageable);
}
