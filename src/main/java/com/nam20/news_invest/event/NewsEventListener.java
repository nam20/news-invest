package com.nam20.news_invest.event;

import com.nam20.news_invest.entity.NewsArticle;
import com.nam20.news_invest.entity.NotificationType;
import com.nam20.news_invest.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleNewsPublishedEvent(NewsPublishedEvent event) {
        NewsArticle newsArticle = event.getNewsArticle();
        log.info("News published event received for article: {}", newsArticle.getTitle());

        String title = "새로운 뉴스 기사 발행";
        String content = String.format("'%s' 기사가 발행되었습니다.", newsArticle.getTitle());
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        notificationService.createNotification(title, content, NotificationType.NEWS, expiresAt);
    }
}
