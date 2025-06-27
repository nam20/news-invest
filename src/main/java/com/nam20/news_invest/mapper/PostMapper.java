package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PostResponse;
import com.nam20.news_invest.entity.Post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.name", target = "userName")
    PostResponse toDto(Post post);
}