package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.CommentCreateRequest;
import com.nam20.news_invest.dto.CommentResponse;
import com.nam20.news_invest.dto.CommentUpdateRequest;
import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.entity.Comment;
import com.nam20.news_invest.entity.Post;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.exception.UnauthorizedOwnershipException;
import com.nam20.news_invest.mapper.CommentMapper;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.repository.CommentRepository;
import com.nam20.news_invest.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final PaginationMetaMapper paginationMetaMapper;

    public PaginationResponse<CommentResponse> retrieveCommentsByPost(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("groupNumber").descending()
                .and(Sort.by("groupOrder")));

        Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageable);

        List<CommentResponse> commentResponses = commentsPage
                .getContent()
                .stream()
                .map(commentMapper::toDto)
                .toList();

        return new PaginationResponse<>(commentResponses,
                paginationMetaMapper.toPaginationMeta(commentsPage));
    }

    public CommentResponse retrieveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        return commentMapper.toDto(comment);
    }

    public PaginationResponse<CommentResponse> retrieveCommentsByUserId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Comment> commentsPage = commentRepository.findByUserId(postId, pageable);

        List<CommentResponse> commentResponses = commentsPage
                .getContent()
                .stream()
                .map(commentMapper::toDto)
                .toList();

        return new PaginationResponse<>(commentResponses,
                paginationMetaMapper.toPaginationMeta(commentsPage));
    }

    @Transactional
    public CommentResponse createComment(CommentCreateRequest requestDto, User user) {
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
                    .orElseThrow(() -> new ResourceNotFoundException("id " + parentCommentId));

            commentBuilder.parentComment(parentComment);
            commentBuilder.depth(parentComment.getDepth() + 1);
            commentBuilder.groupNumber(parentComment.getGroupNumber());

            int lastGroupOrder = commentRepository.findMaxGroupOrderByParentCommentId(parentCommentId)
                    .orElse(parentComment.getGroupOrder());

            commentBuilder.groupOrder(lastGroupOrder + 1);

            commentRepository.incrementGroupOrder(parentComment.getGroupNumber(), lastGroupOrder);

        } else {
            int nextGroupNumber = commentRepository.findTopByOrderByGroupNumberDesc()
                    .map(lastOrderComment -> lastOrderComment.getGroupNumber() + 1)
                    .orElse(0);

            commentBuilder.groupNumber(nextGroupNumber);
            commentBuilder.depth(0);
            commentBuilder.groupOrder(0);
        }

        Comment comment = commentBuilder.build();

        Comment savedComment = commentRepository.save(comment);

        post.incrementCommentCount();
        postRepository.save(post);

        return commentMapper.toDto(savedComment);
    }

    @Transactional
    public CommentResponse updateComment(Long id, CommentUpdateRequest requestDto, User user) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        if (!existingComment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedOwnershipException("id " + id);
        }

        existingComment.updateContent(requestDto.getContent());

        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.toDto(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id " + id));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedOwnershipException("id " + id);
        }

        commentRepository.deleteById(id);

        Post post = comment.getPost();
        post.decrementCommentCount();
        postRepository.save(post);
    }
}
