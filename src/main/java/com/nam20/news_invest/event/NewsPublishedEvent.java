package com.nam20.news_invest.event;

import com.nam20.news_invest.entity.NewsArticle;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewsPublishedEvent extends ApplicationEvent {

    private final NewsArticle newsArticle;

    public NewsPublishedEvent(Object source, NewsArticle newsArticle) {
        super(source);
        this.newsArticle = newsArticle;
    }
}
