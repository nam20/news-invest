package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.CommentResponse;
import com.nam20.news_invest.dto.PostResponse;
import com.nam20.news_invest.dto.UserResponse;
import com.nam20.news_invest.dto.UserUpdateRequest;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.CommentService;
import com.nam20.news_invest.service.PostService;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> retrieveUsers() {
        return ResponseEntity.ok(userService.retrieveUsers());
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
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.updateUser(id, requestDto, user));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostResponse>> retrievePosts(@PathVariable Long id) {
        return ResponseEntity.ok(postService.retrievePostsByUserId(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> retrieveComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.retrieveCommentsByUserId(id));
    }

}
