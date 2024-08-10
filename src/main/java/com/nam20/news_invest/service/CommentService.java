package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.CommentDto;
import com.nam20.news_invest.entity.Comment;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.CommentMapper;
import com.nam20.news_invest.repository.CommentRepository;
import com.nam20.news_invest.repository.PostRepository;
import com.nam20.news_invest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                          UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<CommentDto> retrieveComments() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentDto retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        return commentMapper.toDto(comment);
    }

    public List<CommentDto> retrieveCommentsByUserId(Long postId) {
        return commentRepository.findByUserId(postId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentDto createComment(Long userId, Long postId, Comment comment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("id " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("id " + postId));

        comment.setUser(user);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toDto(savedComment);
    }

    public CommentDto updateComment(Long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        existingComment.setContent(comment.getContent());
        existingComment.setUpdatedAt(LocalDateTime.now());

        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.toDto(updatedComment);
    }

    public void deleteComment(Long id) {
        commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        commentRepository.deleteById(id);
    }
}
