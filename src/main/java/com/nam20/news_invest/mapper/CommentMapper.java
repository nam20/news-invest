package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.CommentResponse;
import com.nam20.news_invest.entity.Comment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMapper() {
        modelMapper.typeMap(Comment.class, CommentResponse.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getName(), CommentResponse::setUserName);
        });
    }

    public CommentResponse toDto(Comment comment) {
        return modelMapper.map(comment, CommentResponse.class);
    }
}
