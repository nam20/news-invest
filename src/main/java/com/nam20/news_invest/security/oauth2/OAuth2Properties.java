package com.nam20.news_invest.security.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.security")
@Getter
@Setter
public class OAuth2Properties {

    private List<String> authorizedRedirectUris = new ArrayList<>();

} 