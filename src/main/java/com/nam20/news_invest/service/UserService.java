package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.RegisterDto;
import com.nam20.news_invest.dto.UserDto;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.exception.AlreadyExistsException;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.mapper.UserMapper;
import com.nam20.news_invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> retrieveUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto retrieveUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        return userMapper.toDto(user);
    }

    public UserDto createUser(RegisterDto registerDto) {

        if (userRepository.existsByName(registerDto.getName())) {
            throw new AlreadyExistsException("name is taken");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new AlreadyExistsException("email is taken");
        }

        User user = User.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        userRepository.deleteById(id);
    }

    public UserDto updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }

    public UserDto retrieveUserByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("name: " + name));

        return userMapper.toDto(user);
    }
}
