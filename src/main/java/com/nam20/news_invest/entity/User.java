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
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"posts", "comments"})
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

    // Role for authorization (e.g., "ROLE_USER", "ROLE_ADMIN")
    private String role;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    public User(String name, String email, String password, String oauth2Id, String profilePictureUrl, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.oauth2Id = oauth2Id;
        this.profilePictureUrl = profilePictureUrl;
        this.role = role != null ? role : "ROLE_USER"; // Default role to ROLE_USER
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return authorities based on the user's role
        if (this.role == null) {
            return Collections.emptyList(); // Or return default role if role cannot be null
        }
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
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
