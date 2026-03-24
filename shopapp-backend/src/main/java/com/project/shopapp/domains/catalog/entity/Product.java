package com.project.shopapp.domains.catalog.entity;

import com.project.shopapp.domains.catalog.enums.ProductType;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Product extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shop_id", nullable = false)
    private Integer shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", insertable = false, updatable = false)
    private Shop shop;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @Column(name = "brand_id")
    private Integer brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private Brand brand;

    @Column(name = "name", length = 350, nullable = false)
    private String name;

    @Column(name = "slug", length = 350)
    private String slug;

    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "min_price", precision = 15, scale = 2)
    private BigDecimal minPrice;

    @Column(name = "max_price", precision = 15, scale = 2)
    private BigDecimal maxPrice;

    @Column(name = "thumbnail", length = 255)
    private String thumbnail;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    @Builder.Default
    private ProductType productType = ProductType.OWN;

    @Column(name = "warranty_period")
    @Builder.Default
    private Integer warrantyPeriod = 12;

    @Column(name = "quantity")
    @Builder.Default
    private Integer quantity = 0;

    @Column(name = "reserved_quantity")
    @Builder.Default
    private Integer reservedQuantity = 0;

    @Column(name = "total_sold")
    @Builder.Default
    private Integer totalSold = 0;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "specs", columnDefinition = "json")
    private Map<String, Object> specs; // Dùng Map để linh hoạt nhận mọi file JSON config

    @Column(name = "is_imei_tracked")
    @Builder.Default
    private Boolean isImeiTracked = true;

    @Column(name = "meta_title", length = 255)
    private String metaTitle;

    @Column(name = "meta_description", columnDefinition = "TEXT")
    private String metaDescription;

    @Column(name = "is_featured")
    @Builder.Default
    private Boolean isFeatured = false;

    @Column(name = "rating_avg")
    @Builder.Default
    private Float ratingAvg = 0f;

    @Column(name = "review_count")
    @Builder.Default
    private Integer reviewCount = 0;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    // --- VIRTUAL COLUMNS: Không cho phép JPA ghi vào DB ---
    @Column(name = "v_ram", insertable = false, updatable = false)
    private String vRam;

    @Column(name = "v_storage", insertable = false, updatable = false)
    private String vStorage;

    @Version
    @Column(name = "version")
    @Builder.Default
    private Integer version = 0;

    // Hàm hỗ trợ kiểm tra tồn kho khả dụng
    public Integer getAvailableQuantity() {
        return Math.max(0, this.quantity - this.reservedQuantity);
    }
}