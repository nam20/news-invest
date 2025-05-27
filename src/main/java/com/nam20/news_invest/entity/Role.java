package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"users"})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // Many-to-Many relationship with User
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    // Constructor for Lombok Builder
    @Builder
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Helper method to add a single user
    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        if (!this.users.contains(user)) {
            this.users.add(user);
        }
    }
} 