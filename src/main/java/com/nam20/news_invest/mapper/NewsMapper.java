package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.NewsApiResponseArticle;
import com.nam20.news_invest.entity.News;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsMapper {

    private final ModelMapper modelMapper;

    public News toEntity(NewsApiResponseArticle article) {
        return modelMapper.map(article, News.class);
    }
}
