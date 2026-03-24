package com.project.shopapp.domains.sales.entity;

import com.project.shopapp.domains.sales.enums.OrderShopStatus;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders_shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderShop extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_shop_code", length = 50, nullable = false)
    private String orderShopCode;

    @Column(name = "parent_order_id", nullable = false)
    private Long parentOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_order_id", insertable = false, updatable = false)
    private Order parentOrder;

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", insertable = false, updatable = false)
    private Shop shop;

    @Column(name = "shipping_method", length = 100)
    private String shippingMethod;

    @Column(name = "shipping_fee", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "sub_total", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal subTotal = BigDecimal.ZERO;

    @Column(name = "admin_commission", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal adminCommission = BigDecimal.ZERO;

    @Column(name = "shop_income", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal shopIncome = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private OrderShopStatus status = OrderShopStatus.PENDING;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    @Column(name = "carrier_name", length = 100)
    private String carrierName;

    @Column(name = "total_tax_amount", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal totalTaxAmount = BigDecimal.ZERO;

    @Column(name = "shop_note", columnDefinition = "TEXT")
    private String shopNote;
}