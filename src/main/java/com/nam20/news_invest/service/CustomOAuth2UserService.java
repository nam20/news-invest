package com.nam20.news_invest.service;

import com.nam20.news_invest.entity.User; // Assuming your User entity is here
import com.nam20.news_invest.security.oauth2.CustomOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.nam20.news_invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository; // Add UserRepository dependency

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

        Optional<User> existingUserOptional = userRepository.findByOauth2Id(id);

        User user;
        if (existingUserOptional.isPresent()) {
            user = existingUserOptional.get();
            user.setName(name);
            user.setEmail(email);
            user.setProfilePictureUrl(pictureUrl);
        } else {
            user = User.builder()
                    .oauth2Id(id)
                    .name(name)
                    .email(email)
                    .profilePictureUrl(pictureUrl)
                    .role("ROLE_USER") // Assuming default role
                    .build();
        }

        User savedUser = userRepository.save(user);

        // Create and return CustomOAuth2User
        // You might need to adapt this if your User entity doesn't match CustomOAuth2User fields exactly
        return new CustomOAuth2User(
                registrationId,
                savedUser.getOauth2Id(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getProfilePictureUrl(),
                Collections.singleton(new SimpleGrantedAuthority(savedUser.getRole())),
                attributes
        );
    }
} 