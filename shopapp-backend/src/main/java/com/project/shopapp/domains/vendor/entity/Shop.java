package com.project.shopapp.domains.vendor.entity;

import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.vendor.enums.ShopStatus;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "shops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Shop extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private User owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "commission_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal commissionRate = new BigDecimal("5.00");

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private ShopStatus status = ShopStatus.PENDING;

    @Column(name = "rating_avg")
    @Builder.Default
    private Float ratingAvg = 5.0f;

    @Column(name = "total_orders")
    @Builder.Default
    private Integer totalOrders = 0;

    @Version
    @Column(name = "version")
    @Builder.Default
    private Integer version = 0;
}