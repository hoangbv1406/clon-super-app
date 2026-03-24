package com.project.shopapp.domains.inventory.entity;

import com.project.shopapp.domains.catalog.entity.Product;
import com.project.shopapp.domains.catalog.entity.ProductVariant;
import com.project.shopapp.domains.inventory.enums.ItemStatus;
import com.project.shopapp.domains.sales.entity.Order;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "product_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductItem extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "variant_id")
    private Integer variantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    private ProductVariant variant;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", insertable = false, updatable = false)
    private Supplier supplier;

    @Column(name = "order_id")
    private Long orderId;

    // Entity Order được map lỏng lẻo vì nó thuộc Domain khác (Sales)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @Column(name = "imei_code", length = 50, nullable = false)
    private String imeiCode;

    @Column(name = "inbound_price", precision = 15, scale = 2)
    private BigDecimal inboundPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private ItemStatus status = ItemStatus.AVAILABLE;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes", columnDefinition = "json")
    private Map<String, Object> attributes;

    @Column(name = "import_date")
    @Builder.Default
    private LocalDateTime importDate = LocalDateTime.now();

    @Column(name = "sold_date")
    private LocalDateTime soldDate;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Version
    @Column(name = "version")
    @Builder.Default
    private Integer version = 0;
}