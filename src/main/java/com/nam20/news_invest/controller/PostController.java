package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.PostDto;
import com.nam20.news_invest.dto.PostRequest;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> retrievePosts() {
        return postService.retrievePosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> retrievePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.retrievePost(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
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
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest updateRequest,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.updatePost(id, updateRequest, user));
    }
}
