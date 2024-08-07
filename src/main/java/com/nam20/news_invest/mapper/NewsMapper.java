package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.NewsApiResponseArticle;
import com.nam20.news_invest.entity.News;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static News toEntity(NewsApiResponseArticle article) {
        return modelMapper.map(article, News.class);
    }
}
