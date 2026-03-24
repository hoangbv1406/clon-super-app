package com.project.shopapp.domains.social.entity;

import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_follows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserFollow extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "follower_id", nullable = false)
    private Integer followerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", insertable = false, updatable = false)
    private User follower;

    @Column(name = "following_user_id")
    private Integer followingUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id", insertable = false, updatable = false)
    private User followingUser;

    @Column(name = "following_shop_id")
    private Integer followingShopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_shop_id", insertable = false, updatable = false)
    private Shop followingShop;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}