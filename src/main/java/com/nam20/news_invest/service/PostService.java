package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.PostResponse;
import com.nam20.news_invest.dto.PostRequest;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.exception.UnauthorizedOwnershipException;
import com.nam20.news_invest.mapper.PostMapper;
import com.nam20.news_invest.repository.PostRepository;
import com.nam20.news_invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public List<PostResponse> retrievePosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostResponse retrievePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        return postMapper.toDto(post);
    }

    public List<PostResponse> retrievePostsByUserId(Long userId) {
        return postRepository.findByUserId(userId)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostResponse createPost(PostRequest createRequest, User user) {
        Post post = Post.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .category(createRequest.getCategory())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return postMapper.toDto(savedPost);
    }

    public void deletePost(Long id, User user) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedOwnershipException("id: " + id);
        }

        postRepository.deleteById(id);
    }

    public PostResponse updatePost(Long id, PostRequest updateRequest, User user) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        if (!existingPost.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedOwnershipException("id: " + id);
        }

        existingPost.updateDetails(updateRequest.getTitle(),
                updateRequest.getContent(), updateRequest.getCategory());

        Post updatedPost = postRepository.save(existingPost);

        return postMapper.toDto(updatedPost);
    }
}
