package com.project.shopapp.domains.sales.entity;

import com.project.shopapp.domains.catalog.entity.Product;
import com.project.shopapp.domains.catalog.entity.ProductVariant;
import com.project.shopapp.domains.inventory.entity.ProductItem;
import com.project.shopapp.domains.inventory.entity.Supplier;
import com.project.shopapp.domains.sales.enums.OrderItemStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @Column(name = "order_shop_id")
    private Integer orderShopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shop_id", insertable = false, updatable = false)
    private OrderShop orderShop;

    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "variant_id")
    private Integer variantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    private ProductVariant variant;

    @Column(name = "product_item_id")
    private Integer productItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", insertable = false, updatable = false)
    private ProductItem productItem;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", insertable = false, updatable = false)
    private Supplier supplier;

    // --- CÁC TRƯỜNG SNAPSHOT (Lưu cứng không thay đổi) ---
    @Column(name = "product_name", length = 350)
    private String productName;

    @Column(name = "variant_name", length = 255)
    private String variantName;

    @Column(name = "product_thumbnail_snapshot", length = 300)
    private String productThumbnailSnapshot;

    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "number_of_products")
    @Builder.Default
    private Integer numberOfProducts = 1;

    @Column(name = "total_money", precision = 15, scale = 2)
    private BigDecimal totalMoney;

    @Column(name = "discount_amount", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "coupon_id")
    private Integer couponId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuration", columnDefinition = "json")
    private Map<String, Object> configuration;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal taxRate = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal taxAmount = BigDecimal.ZERO;

    // --- NGHIỆP VỤ KẾ TOÁN (Dropshipping / Consignment) ---
    @Column(name = "cost_price", precision = 15, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "is_settled")
    @Builder.Default
    private Boolean isSettled = false;

    @Column(name = "settlement_date")
    private LocalDateTime settlementDate;

    @Column(name = "settlement_ref", length = 100)
    private String settlementRef;

    @Column(name = "settlement_note", length = 255)
    private String settlementNote;

    @Column(name = "warranty_expire_date")
    private LocalDate warrantyExpireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    @Builder.Default
    private OrderItemStatus itemStatus = OrderItemStatus.NORMAL;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}