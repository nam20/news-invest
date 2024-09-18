package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.CommentCreateRequest;
import com.nam20.news_invest.dto.CommentResponse;
import com.nam20.news_invest.dto.CommentUpdateRequest;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<CommentResponse>> retrieveComments(
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(commentService.retrieveComments(page, PAGE_SIZE));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PaginationResponse<CommentResponse>> retrieveCommentsByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(commentService.retrieveCommentsByPost(postId, page, PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> retrieveComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.retrieveComment(id));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @Valid @RequestBody CommentCreateRequest requestDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(commentService.createComment(requestDto, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentUpdateRequest requestDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(commentService.updateComment(id, requestDto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal User user) {
        commentService.deleteComment(id, user);
        return ResponseEntity.noContent().build();
    }
}
