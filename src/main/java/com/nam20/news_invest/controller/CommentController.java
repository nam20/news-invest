package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.CommentCreateRequest;
import com.nam20.news_invest.dto.CommentDto;
import com.nam20.news_invest.dto.CommentUpdateRequest;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> retrieveComments() {
        return ResponseEntity.ok(commentService.retrieveComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> retrieveComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.retrieveComment(id));
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CommentCreateRequest requestDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(commentService.createComment(requestDto, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
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