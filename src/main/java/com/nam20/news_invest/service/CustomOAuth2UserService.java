package com.nam20.news_invest.service;

import com.nam20.news_invest.entity.User; // Assuming your User entity is here
import com.nam20.news_invest.security.oauth2.CustomOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuth2UserService oauth2UserService; // Inject the new OAuth2UserService

    public CustomOAuth2UserService(OAuth2UserService oauth2UserService) {
        this.oauth2UserService = oauth2UserService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Extract user attributes based on registrationId (currently only Google supported)
        String id = null;
        String email = null;
        String name = null;
        String pictureUrl = null;

        if ("google".equals(registrationId)) {
            id = (String) attributes.get("sub");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            pictureUrl = (String) attributes.get("picture");
        } else {
            // Handle other providers if needed
        }

        // Save or update user information in your database
        User user = oauth2UserService.saveOrUpdateUser(id, email, name, pictureUrl, registrationId);

        // Create and return CustomOAuth2User
        // You might need to adapt this if your User entity doesn't match CustomOAuth2User fields exactly
        return new CustomOAuth2User(
                registrationId,
                user.getOauth2Id(), // Assuming your User entity stores the OAuth2 ID
                user.getEmail(),
                user.getName(),
                user.getProfilePictureUrl(), // Assuming your User entity stores the picture URL
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())), // Assuming your User entity stores the role
                attributes
        );
    }
} 