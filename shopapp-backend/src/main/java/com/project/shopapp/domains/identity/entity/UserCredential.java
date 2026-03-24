package com.project.shopapp.domains.identity.entity;

import com.project.shopapp.domains.identity.enums.AuthenticatorType;
import com.project.shopapp.shared.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_credentials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserCredential extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "credential_id", nullable = false, columnDefinition = "TEXT")
    private String credentialId;

    @Column(name = "public_key", nullable = false, columnDefinition = "TEXT")
    private String publicKey;

    @Column(name = "sign_count")
    @Builder.Default
    private Integer signCount = 0;

    @Column(name = "device_label")
    private String deviceLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "authenticator_type")
    @Builder.Default
    private AuthenticatorType authenticatorType = AuthenticatorType.PLATFORM;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;
}