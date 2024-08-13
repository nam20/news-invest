package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.CommentDto;
import com.nam20.news_invest.dto.PostDto;
import com.nam20.news_invest.dto.UserDto;
import com.nam20.news_invest.entity.Comment;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.CommentService;
import com.nam20.news_invest.service.PostService;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDto>> retrieveUsers() {
        return ResponseEntity.ok(userService.retrieveUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> retrieveUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.retrieveUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDto>> retrievePosts(@PathVariable Long id) {
        return ResponseEntity.ok(postService.retrievePostsByUserId(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> retrieveComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.retrieveCommentsByUserId(id));
    }

    // TODO: 시큐리티 적용 후 변경
    @PostMapping("/{id}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Long id, @Valid @RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(id, post));
    }

    // TODO: 시큐리티 적용 후 변경
    @PostMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long userId, @PathVariable Long postId,
                                                 @Valid @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.createComment(userId, postId, comment));
    }
}
