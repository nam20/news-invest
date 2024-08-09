package com.nam20.news_invest.service;

import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> retrieveUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));
    }

    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }
}
