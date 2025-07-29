package com.nam20.news_invest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notification_deliveries")
public class NotificationDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String channel; // "WEB", "EMAIL", "PUSH"

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime deliveredAt;

    private LocalDateTime readAt;

    @Column(nullable = false)
    private String status; // "DELIVERED", "FAILED", "READ"

    @Builder
    public NotificationDelivery(Notification notification, User user, String channel, String status) {
        this.notification = notification;
        this.user = user;
        this.channel = channel;
        this.status = status;
    }
}
