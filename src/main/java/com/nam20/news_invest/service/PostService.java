package com.nam20.news_invest.service;

import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> retrievePosts() {
        return postRepository.findAll();
    }

    public Post retrievePost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));
    }

    public List<Post> retrievePostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public Post createPost(User user, Post post) {
        post.setUser(user);
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        postRepository.deleteById(id);
    }

    public Post updatePost(Long id, Post post) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setUpdatedAt(post.getUpdatedAt());
        existingPost.setCategory(post.getCategory());

        return postRepository.save(existingPost);
    }
}
