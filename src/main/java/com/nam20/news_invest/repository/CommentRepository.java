package com.nam20.news_invest.repository;

import com.nam20.news_invest.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByUserId(Long userId, Pageable pageable);
    @EntityGraph(attributePaths = {"user"})
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    Optional<Comment> findTopByOrderByGroupNumberDesc();

    @Transactional
    @Modifying
    @Query(value = "UPDATE comments c SET group_order = c.group_order + 1 " +
            "WHERE c.group_number = :groupNumber AND c.group_order > :groupOrder",
            nativeQuery = true)
    void incrementGroupOrder(
            @Param("groupNumber") Integer groupNumber,
            @Param("groupOrder") Integer groupOrder
    );

    @Query(value = "SELECT MAX(c.group_order) FROM comments c WHERE c.parent_comment_id = :parentCommentId",
            nativeQuery = true)
    Optional<Integer> findMaxGroupOrderByParentCommentId(@Param("parentCommentId") Long parentCommentId);
}
