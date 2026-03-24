package com.project.shopapp.domains.social.entity;

import com.project.shopapp.domains.social.enums.InteractionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_interactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Column(name = "product_id", updatable = false)
    private Integer productId;

    @Column(name = "post_id", updatable = false)
    private Long postId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, updatable = false)
    private InteractionType actionType;

    @Column(name = "duration_ms", updatable = false)
    @Builder.Default
    private Integer durationMs = 0;

    @Column(name = "ip_address", length = 50, updatable = false)
    private String ipAddress;

    @Column(name = "device_id", length = 255, updatable = false)
    private String deviceId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}