package com.nam20.news_invest.controller;

import com.nam20.news_invest.dto.CommentDto;
import com.nam20.news_invest.entity.Comment;
import com.nam20.news_invest.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    // TODO: 시큐리티 적용 후 변경
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @Valid @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(id, comment));
    }

    // TODO: 시큐리티 적용 후 변경
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
