package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.CommentDto;
import com.nam20.news_invest.entity.Comment;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentDto toDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
}
