package com.nam20.news_invest.event;

import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.enums.NotificationType;
import com.nam20.news_invest.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RiskEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleRiskExceededEvent(RiskExceededEvent event) {
        User user = event.getUser();
        String riskType = event.getRiskType();
        String message = event.getMessage();

        log.info("Risk exceeded event received for user: {}, risk type: {}", user.getUsername(), riskType);

        String title = "리스크 한도 초과 알림";
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(7); // 리스크 알림은 7일간 유효

        notificationService.createNotificationForUser(user, title, message, NotificationType.RISK_EXCEEDED, expiresAt);
    }
}
