package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_notification_settings")
public class UserNotificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private boolean enabled = true;

    // e.g., "REAL_TIME", "DAILY_SUMMARY"
    private String frequency;

    // e.g., "WEB", "EMAIL", "PUSH"
    private String preferredChannel;

    @Builder
    public UserNotificationSetting(User user, NotificationType notificationType, boolean enabled, String frequency, String preferredChannel) {
        this.user = user;
        this.notificationType = notificationType;
        this.enabled = enabled;
        this.frequency = frequency;
        this.preferredChannel = preferredChannel;
    }
}
