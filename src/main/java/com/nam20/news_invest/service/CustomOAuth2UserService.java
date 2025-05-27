package com.nam20.news_invest.service;

import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.entity.Role;
import com.nam20.news_invest.security.oauth2.CustomOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.nam20.news_invest.repository.UserRepository;
import com.nam20.news_invest.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

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
        // Find the default role (e.g., ROLE_USER)
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                                        .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found")); // Handle role not found

        if (existingUserOptional.isPresent()) {
            user = existingUserOptional.get();
            user.setName(name);
            user.setEmail(email);
            user.setProfilePictureUrl(pictureUrl);
            // Ensure the default role is present if not already
            if (!user.getRoles().contains(defaultRole)) {
                 user.addRole(defaultRole);
            }
        } else {
            user = User.builder()
                    .oauth2Id(id)
                    .name(name)
                    .email(email)
                    .profilePictureUrl(pictureUrl)
                    // Assign a list containing the default role
                    .roles(Collections.singletonList(defaultRole))
                    .build();
        }

        User savedUser = userRepository.save(user);

        // Create and return CustomOAuth2User
        // Get authorities from the roles list
        return new CustomOAuth2User(
                registrationId,
                savedUser.getOauth2Id(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getProfilePictureUrl(),
                // Map roles to SimpleGrantedAuthority
                savedUser.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()), // Collect as List
                attributes
        );
    }
} 