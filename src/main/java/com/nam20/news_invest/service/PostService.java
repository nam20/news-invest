package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.dto.PostRequest;
import com.nam20.news_invest.dto.PostResponse;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.exception.UnauthorizedOwnershipException;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.mapper.PostMapper;
import com.nam20.news_invest.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PaginationMetaMapper paginationMetaMapper;

    public PaginationResponse<PostResponse> retrievePosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Post> postsPage = postRepository.findAll(pageable);

        List<PostResponse> postResponses = postsPage
                .getContent()
                .stream()
                .map(postMapper::toDto)
                .toList();

        return new PaginationResponse<>(postResponses,
                paginationMetaMapper.toPaginationMeta(postsPage));
    }

    public PostResponse retrievePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        return postMapper.toDto(post);
    }

    public PaginationResponse<PostResponse> retrievePostsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Post> postsPage = postRepository.findByUserId(userId, pageable);

        List<PostResponse> postResponses = postsPage
                .getContent()
                .stream()
                .map(postMapper::toDto)
                .toList();

        return new PaginationResponse<>(postResponses,
                paginationMetaMapper.toPaginationMeta(postsPage));
    }

    @Transactional
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

    @Transactional
    public void deletePost(Long id, User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedOwnershipException("id: " + id);
        }

        postRepository.deleteById(id);
    }

    @Transactional
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
