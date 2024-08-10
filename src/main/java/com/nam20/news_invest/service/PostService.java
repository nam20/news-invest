package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.PostDto;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.PostMapper;
import com.nam20.news_invest.repository.PostRepository;
import com.nam20.news_invest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }

    public List<PostDto> retrievePosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostDto retrievePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        return postMapper.toDto(post);
    }

    public List<PostDto> retrievePostsByUserId(Long userId) {
        return postRepository.findByUserId(userId)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostDto createPost(Long userId, Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + userId));

        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        return postMapper.toDto(savedPost);
    }

    public void deletePost(Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        postRepository.deleteById(id);
    }

    public PostDto updatePost(Long id, Post post) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setCategory(post.getCategory());
        existingPost.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(existingPost);

        return postMapper.toDto(updatedPost);
    }
}
