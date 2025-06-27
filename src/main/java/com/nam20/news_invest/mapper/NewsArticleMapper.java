package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.NewsArticleResponse;
import com.nam20.news_invest.entity.NewsArticle;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsArticleMapper {
     NewsArticleResponse toDto(NewsArticle newsArticle);
}
