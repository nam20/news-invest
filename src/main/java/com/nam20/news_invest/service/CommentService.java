package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.CommentCreateRequest;
import com.nam20.news_invest.dto.CommentDto;
import com.nam20.news_invest.dto.CommentUpdateRequest;
import com.nam20.news_invest.entity.Comment;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.exception.UnauthorizedOwnershipException;
import com.nam20.news_invest.mapper.CommentMapper;
import com.nam20.news_invest.repository.CommentRepository;
import com.nam20.news_invest.repository.PostRepository;
import com.nam20.news_invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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

    public CommentDto createComment(CommentCreateRequest requestDto, User user) {
        Long postId = requestDto.getPostId();
        Long parentCommentId = requestDto.getParentCommentId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("id " + postId));

        Comment.CommentBuilder commentBuilder = Comment.builder()
                .content(requestDto.getContent())
                .user(user)
                .post(post);

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElse(null);

            commentBuilder.parentComment(parentComment);
        }

        Comment comment = commentBuilder.build();

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toDto(savedComment);
    }

    public CommentDto updateComment(Long id, CommentUpdateRequest requestDto, User user) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        if (!existingComment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedOwnershipException("id " + id);
        }

        existingComment.setContent(requestDto.getContent());

        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.toDto(updatedComment);
    }

    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedOwnershipException("id " + id);
        }

        commentRepository.deleteById(id);
    }
}
