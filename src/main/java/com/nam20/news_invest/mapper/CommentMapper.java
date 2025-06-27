package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.CommentResponse;
import com.nam20.news_invest.entity.Comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.name", target = "userName")
    CommentResponse toDto(Comment comment);
}
