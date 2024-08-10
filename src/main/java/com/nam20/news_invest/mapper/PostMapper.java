package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PostDto;
import com.nam20.news_invest.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostDto toDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
