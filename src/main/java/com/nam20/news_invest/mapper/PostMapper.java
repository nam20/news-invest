package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PostDetailResponse;
import com.nam20.news_invest.dto.PostResponse;
import com.nam20.news_invest.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostResponse toDto(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }

    public PostDetailResponse toDetailDto(Post post) {
        return modelMapper.map(post, PostDetailResponse.class);
    }
}
