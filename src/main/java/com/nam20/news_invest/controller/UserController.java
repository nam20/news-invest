package com.nam20.news_invest.controller;

import com.nam20.news_invest.entity.User;
import com.nam20.news_invest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> retrieveUsers() {
        return userService.retrieveUsers();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable Long id) {
        return userService.retrieveUser(id);
    }

    @PostMapping("/users")
    public User saveUser(@Valid @RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updateUser = userService.retrieveUser(id);

        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setUpdatedAt(LocalDateTime.now());

        return userService.updateUser(updateUser);
    }
}
