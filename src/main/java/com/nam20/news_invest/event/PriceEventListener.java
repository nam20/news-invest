package com.nam20.news_invest.event;

import com.nam20.news_invest.enums.NotificationType;
import com.nam20.news_invest.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceEventListener {

    private final NotificationService notificationService;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handlePriceSpikeEvent(PriceSpikeEvent event) {
        String assetSymbol = event.getAssetSymbol();
        double percentageChange = event.getPercentageChange();
        String direction = percentageChange > 0 ? "급등" : "급락";
        String formattedPercentage = String.format("%.2f%%", Math.abs(percentageChange));

        log.info("Price spike event received for asset: {}, change: {}", assetSymbol, formattedPercentage);

        String title = String.format("%s 가격 %s", assetSymbol, direction);
        String content = String.format("%s의 가격이 %s %s하여 현재 가격은 %s원 입니다.", assetSymbol, formattedPercentage, direction,
                event.getNewPrice().toString());
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        notificationService.createNotification(title, content, NotificationType.PRICE_SPIKE, expiresAt);

        // 급등일 경우 투자 기회 이벤트 발행
        if ("급등".equals(direction)) {
            String opportunityDetails = String.format("종목 '%s'의 가격이 급등했습니다.", assetSymbol);
            InvestmentOpportunityEvent opportunityEvent = new InvestmentOpportunityEvent(this, opportunityDetails);

            eventPublisher.publishEvent(opportunityEvent);
        }
    }
}
