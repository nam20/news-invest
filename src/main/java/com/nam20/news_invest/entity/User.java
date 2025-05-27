package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"posts", "comments", "roles"})
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // Password is not nullable for standard login, but might be null for OAuth2 only users
    private String password;

    @Column(unique = true)
    private String oauth2Id; // Store OAuth2 provider user ID

    private String profilePictureUrl; // Store profile picture URL from OAuth2

    // Many-to-Many relationship with Role
    @ManyToMany(fetch = FetchType.EAGER) // Eager fetch is common for roles
    @JoinTable(
        name = "user_roles", // Name of the join table
        joinColumns = @JoinColumn(name = "user_id"), // Foreign key in join table referencing User
        inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key in join table referencing Role
    )
    private List<Role> roles = new ArrayList<>(); // Initialize roles list

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    // Modified constructor to accept List of Role objects or individual roles
    public User(String name, String email, String password, String oauth2Id, String profilePictureUrl, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.oauth2Id = oauth2Id;
        this.profilePictureUrl = profilePictureUrl;
        if (roles != null) {
            this.roles = roles;
        } else {
            this.roles = new ArrayList<>();
        }
    }

    // Helper method to add a single role
    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return authorities based on the user's roles
        if (this.roles == null || this.roles.isEmpty()) {
            return Collections.emptyList();
        }
        return this.roles.stream()
                   .map(role -> new SimpleGrantedAuthority(role.getName()))
                   .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return name;
    }

    // --- UserDetails methods (implement as needed for your security context) ---
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
    // -------------------------------------------------------------------------

}
