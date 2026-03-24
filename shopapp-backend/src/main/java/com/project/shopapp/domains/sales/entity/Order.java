package com.project.shopapp.domains.sales.entity;

import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.sales.enums.OrderChannel;
import com.project.shopapp.domains.sales.enums.OrderStatus;
import com.project.shopapp.domains.sales.enums.PaymentStatus;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Order extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", length = 50, nullable = false)
    private String orderCode;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "pos_session_id")
    private Integer posSessionId;

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "province_code", length = 20)
    private String provinceCode;

    @Column(name = "district_code", length = 20)
    private String districtCode;

    @Column(name = "ward_code", length = 20)
    private String wardCode;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "order_date")
    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "sub_total", precision = 15, scale = 2, nullable = false)
    private BigDecimal subTotal;

    @Column(name = "shipping_fee", precision = 15, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 15, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "total_money", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalMoney;

    @Column(name = "shipping_address", length = 200)
    private String shippingAddress;

    @Column(name = "payment_method", length = 100)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_channel")
    @Builder.Default
    private OrderChannel orderChannel = OrderChannel.ONLINE;

    @Column(name = "active")
    @Builder.Default
    private Boolean active = true;

    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name = "total_cost_price", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal totalCostPrice = BigDecimal.ZERO;

    @Version
    @Column(name = "version")
    @Builder.Default
    private Integer version = 0;
}