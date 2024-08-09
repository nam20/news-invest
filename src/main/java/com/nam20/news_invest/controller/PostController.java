package com.nam20.news_invest.controller;

import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> retrievePosts() {
        return postService.retrievePosts();
    }

    @GetMapping("/{id}")
    public Post retrievePost(Long id) {
        return postService.retrievePost(id);
    }
}
