package com.project.shopapp.domains.vendor.entity;

import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.vendor.enums.EmployeeStatus;
import com.project.shopapp.domains.vendor.enums.ShopEmployeeRole;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "shop_employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ShopEmployee extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", insertable = false, updatable = false)
    private Shop shop;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private ShopEmployeeRole role = ShopEmployeeRole.SALES;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private EmployeeStatus status = EmployeeStatus.ACTIVE;
}