package com.project.shopapp.domains.marketing.entity;

import com.project.shopapp.domains.marketing.enums.ApplicableObjectType;
import com.project.shopapp.domains.marketing.enums.ApplicableRuleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_applicables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CouponApplicable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "coupon_id", nullable = false)
    private Integer couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type", nullable = false)
    private ApplicableObjectType objectType;

    @Column(name = "object_id", nullable = false)
    private Integer objectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicable_type")
    @Builder.Default
    private ApplicableRuleType applicableType = ApplicableRuleType.INCLUDE;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Integer createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}