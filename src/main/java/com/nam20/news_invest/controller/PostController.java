package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.PostDto;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.PostService;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PostDto> retrievePost(Long id) {
        return ResponseEntity.ok(postService.retrievePost(id));
    }

    // TODO: 시큐리티 적용 후 변경
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: 시큐리티 적용 후 변경
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @Valid @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }
}
