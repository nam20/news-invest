package com.nam20.news_invest.event;

import com.nam20.news_invest.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RiskExceededEvent extends ApplicationEvent {

    private final User user;
    private final String riskType;
    private final String message;

    public RiskExceededEvent(Object source, User user, String riskType, String message) {
        super(source);
        this.user = user;
        this.riskType = riskType;
        this.message = message;
    }
}
