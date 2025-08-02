package com.nam20.news_invest.event;

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
public class InvestmentOpportunityEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleInvestmentOpportunity(InvestmentOpportunityEvent event) {
        log.info("Investment opportunity event received: {}", event.getOpportunityDetails());

        String title = "새로운 투자 기회";
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        notificationService.createNotification(
                title,
                event.getOpportunityDetails(),
                NotificationType.INVESTMENT_OPPORTUNITY,
                expiresAt
        );
    }
}
