package com.project.shopapp.domains.after_sales.entity;

import com.project.shopapp.domains.after_sales.enums.RequestType;
import com.project.shopapp.domains.after_sales.enums.WarrantyStatus;
import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.inventory.entity.ProductItem;
import com.project.shopapp.domains.sales.entity.OrderDetail;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "warranty_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class WarrantyRequest extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_code", length = 50, nullable = false)
    private String requestCode;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", insertable = false, updatable = false)
    private Shop shop;

    @Column(name = "order_detail_id", nullable = false)
    private Integer orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id", insertable = false, updatable = false)
    private OrderDetail orderDetail;

    @Column(name = "product_item_id")
    private Integer productItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", insertable = false, updatable = false)
    private ProductItem productItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private WarrantyStatus status = WarrantyStatus.PENDING;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "images", columnDefinition = "json")
    private List<String> images;

    @Column(name = "admin_note", columnDefinition = "TEXT")
    private String adminNote;

    @Column(name = "quantity")
    @Builder.Default
    private Integer quantity = 1;

    @Column(name = "return_tracking_code", length = 100)
    private String returnTrackingCode;

    @Column(name = "refund_amount", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal refundAmount = BigDecimal.ZERO;
}