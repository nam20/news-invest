package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PostDto;
import com.nam20.news_invest.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PostDto toDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
