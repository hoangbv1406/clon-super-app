package com.project.shopapp.domains.catalog.entity;

import com.project.shopapp.domains.catalog.enums.PriceType;
import com.project.shopapp.domains.identity.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false, updatable = false)
    private Integer productId;

    @Column(name = "variant_id", updatable = false)
    private Integer variantId;

    @Column(name = "old_price", precision = 15, scale = 2, updatable = false)
    private BigDecimal oldPrice;

    @Column(name = "new_price", precision = 15, scale = 2, nullable = false, updatable = false)
    private BigDecimal newPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type", updatable = false)
    @Builder.Default
    private PriceType priceType = PriceType.SELLING_PRICE;

    @Column(name = "reason", length = 255, updatable = false)
    private String reason;

    @Column(name = "updated_by", updatable = false)
    private Integer updatedBy;

    // Join để hiển thị tên người đổi giá trên Dashboard Admin
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private User updater;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}