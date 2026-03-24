package com.project.shopapp.domains.affiliate.entity;

import com.project.shopapp.domains.affiliate.enums.AffiliateTransStatus;
import com.project.shopapp.domains.sales.entity.OrderShop; // Nhúng từ module Sales
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "affiliate_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class AffiliateTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "affiliate_link_id", nullable = false, updatable = false)
    private Integer affiliateLinkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliate_link_id", insertable = false, updatable = false)
    private AffiliateLink affiliateLink;

    @Column(name = "order_shop_id", nullable = false, updatable = false)
    private Integer orderShopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shop_id", insertable = false, updatable = false)
    private OrderShop orderShop;

    @Column(name = "amount", precision = 15, scale = 2, nullable = false, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private AffiliateTransStatus status = AffiliateTransStatus.PENDING;

    @Column(name = "payout_date")
    private LocalDateTime payoutDate;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Integer updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}