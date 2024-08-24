package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.*;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.CommentService;
import com.nam20.news_invest.service.PostService;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<UserResponse>> retrieveUsers(
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(userService.retrieveUsers(page, PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> retrieveUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.retrieveUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @AuthenticationPrincipal User user) {
        userService.deleteUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest requestDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.updateUser(id, requestDto, user));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PaginationResponse<PostResponse>> retrievePosts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(postService.retrievePostsByUserId(id, page, PAGE_SIZE));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<PaginationResponse<CommentResponse>> retrieveComments(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(commentService.retrieveCommentsByUserId(id, page, PAGE_SIZE));
    }
}
