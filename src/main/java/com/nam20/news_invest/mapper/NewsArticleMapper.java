package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.entity.NewsArticle;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsArticleMapper {

    private final ModelMapper modelMapper;

    public NewsArticleResponse toDto(NewsArticle newsArticle) {
        return modelMapper.map(newsArticle, NewsArticleResponse.class);
    }
}
