package com.nam20.news_invest.service;

import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final UserRepository userRepository;

    public User saveOrUpdateUser(String oauth2Id, String email, String name, String profilePictureUrl, String registrationId) {
        Optional<User> existingUserOptional = userRepository.findByOauth2Id(oauth2Id);

        User user;
        if (existingUserOptional.isPresent()) {
            user = existingUserOptional.get();
            user.setName(name);
            user.setEmail(email);
            user.setProfilePictureUrl(profilePictureUrl);
        } else {
            user = User.builder()
                    .oauth2Id(oauth2Id)
                    .name(name)
                    .email(email)
                    .profilePictureUrl(profilePictureUrl)
                    .role("ROLE_USER") // Assuming default role
                    .build();
        }

        return userRepository.save(user);
    }
}