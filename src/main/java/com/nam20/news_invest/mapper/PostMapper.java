package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PostResponse;
import com.nam20.news_invest.entity.Post;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMapper() {
        modelMapper.typeMap(Post.class, PostResponse.class).addMappings(mapper -> {
           mapper.map(src -> src.getUser().getName(), PostResponse::setUserName);
        });
    }

    public PostResponse toDto(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }
}
