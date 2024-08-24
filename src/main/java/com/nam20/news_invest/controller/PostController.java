package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.dto.PostRequest;
import com.nam20.news_invest.dto.PostResponse;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<PaginationResponse<PostResponse>> retrievePosts(
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(postService.retrievePosts(page, PAGE_SIZE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> retrievePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.retrievePost(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest createRequest,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(postService.createPost(createRequest, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @AuthenticationPrincipal User user) {
        postService.deletePost(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest updateRequest,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.updatePost(id, updateRequest, user));
    }
}
