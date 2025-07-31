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
public class PriceEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handlePriceSpikeEvent(PriceSpikeEvent event) {
        String assetName = event.getAssetSymbol();
        double percentageChange = event.getPercentageChange();
        String direction = percentageChange > 0 ? "급등" : "급락";
        String formattedPercentage = String.format("%.2f%%", Math.abs(percentageChange));

        log.info("Price spike event received for asset: {}, change: {}", assetName, formattedPercentage);

        String title = String.format("%s 가격 %s", assetName, direction);
        String content = String.format("%s의 가격이 %s하여 현재 가격은 %s원 입니다.", assetName, formattedPercentage, event.getNewPrice().toString());
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        notificationService.createNotification(title, content, NotificationType.PRICE_SPIKE, expiresAt);
    }
}
