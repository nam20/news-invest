package com.nam20.news_invest.service;

import com.nam20.news_invest.entity.Notification;
import com.nam20.news_invest.entity.NotificationDelivery;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.enums.NotificationType;
import com.nam20.news_invest.repository.NotificationDeliveryRepository;
import com.nam20.news_invest.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationDeliveryRepository notificationDeliveryRepository;

    @Transactional
    public void createNotification(String title, String content, NotificationType type, LocalDateTime expiresAt) {
        Notification notification = Notification.builder()
                .title(title)
                .content(content)
                .type(type)
                .expiresAt(expiresAt)
                .status("UNREAD") // 초기 상태를 "UNREAD"로 설정
                .build();
        notificationRepository.save(notification);
    }

    @Transactional
    public void createNotificationForUser(User user, String title, String content, NotificationType type, LocalDateTime expiresAt) {
        Notification notification = Notification.builder()
                .title(title)
                .content(content)
                .type(type)
                .expiresAt(expiresAt)
                .status("UNREAD")
                .build();
        notificationRepository.save(notification);

        NotificationDelivery delivery = NotificationDelivery.builder()
                .notification(notification)
                .user(user)
                .channel("WEB") // 기본 채널을 WEB으로 설정
                .status("DELIVERED")
                .build();
        notificationDeliveryRepository.save(delivery);
    }
}
