package com.nam20.news_invest.controller;

import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.PostService;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<User>> retrieveUsers() {
        return ResponseEntity.ok(userService.retrieveUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> retrieveUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.retrieveUser(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> retrievePosts(@PathVariable Long id) {
        return ResponseEntity.ok(postService.retrievePostsByUserId(id));
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long id, @Valid @RequestBody Post post) {
        User user = userService.retrieveUser(id);
        return ResponseEntity.ok(postService.createPost(user, post));
    }

    @DeleteMapping("/{userId}/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long userId, @PathVariable Long postId) {
        userService.retrieveUser(userId);
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long userId, @PathVariable Long postId, @Valid @RequestBody Post post) {
        userService.retrieveUser(userId);
        return ResponseEntity.ok(postService.updatePost(postId, post));
    }

}
