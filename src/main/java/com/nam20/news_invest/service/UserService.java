package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.PaginationResponse;
import com.nam20.news_invest.dto.RegisterRequest;
import com.nam20.news_invest.dto.UserResponse;
import com.nam20.news_invest.dto.UserUpdateRequest;
import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.entity.Role;
import com.nam20.news_invest.exception.AlreadyExistsException;
import com.nam20.news_invest.exception.ResourceNotFoundException;
import com.nam20.news_invest.exception.UnauthorizedOwnershipException;
import com.nam20.news_invest.mapper.PaginationMetaMapper;
import com.nam20.news_invest.mapper.UserMapper;
import com.nam20.news_invest.repository.UserRepository;
import com.nam20.news_invest.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PaginationMetaMapper paginationMetaMapper;
    private final RoleRepository roleRepository;

    public PaginationResponse<UserResponse> retrieveUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserResponse> userResponses = usersPage
                .getContent()
                .stream()
                .map(userMapper::toDto)
                .toList();

        return new PaginationResponse<>(userResponses,
                paginationMetaMapper.toPaginationMeta(usersPage));
    }

    public UserResponse retrieveUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponse createUser(RegisterRequest registerRequest) {

        if (userRepository.existsByName(registerRequest.getName())) {
            throw new AlreadyExistsException("이미 사용 중인 이름입니다.");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AlreadyExistsException("이미 등록된 이메일 주소입니다.");
        }

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                                        .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found"));

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Collections.singletonList(defaultRole))
                .build();

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public void deleteUser(Long id, User currentUser) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        userRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserResponse updateUser(Long id, UserUpdateRequest requestDto, User currentUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id: " + id));

        existingUser.setName(requestDto.getName());
        existingUser.setEmail(requestDto.getEmail());
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }

    public UserResponse retrieveUserByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("name: " + name));

        return userMapper.toDto(user);
    }
}
