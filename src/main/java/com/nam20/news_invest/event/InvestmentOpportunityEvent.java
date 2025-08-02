package com.nam20.news_invest.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InvestmentOpportunityEvent extends ApplicationEvent {
    private final String opportunityDetails;

    public InvestmentOpportunityEvent(Object source, String opportunityDetails) {
        super(source);
        this.opportunityDetails = opportunityDetails;
    }
}
