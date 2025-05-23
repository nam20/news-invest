package com.nam20.news_invest.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private String registrationId; // e.g., "google"
    private String id;
    private String email;
    private String name;
    private String pictureUrl;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomOAuth2User(String registrationId, String id, String email, String name, String pictureUrl, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.registrationId = registrationId;
        this.id = id;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getRegistrationId() {
        return registrationId;
    }
} 