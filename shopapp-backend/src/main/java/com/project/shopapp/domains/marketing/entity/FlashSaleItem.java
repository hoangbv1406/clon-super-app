package com.project.shopapp.domains.marketing.entity;

import com.project.shopapp.domains.catalog.entity.Product;
import com.project.shopapp.domains.catalog.entity.ProductVariant;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "flash_sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FlashSaleItem extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flash_sale_id", nullable = false)
    private Integer flashSaleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flash_sale_id", insertable = false, updatable = false)
    private FlashSale flashSale;

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

    @Column(name = "promotional_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal promotionalPrice;

    @Column(name = "quantity_limit", nullable = false)
    private Integer quantityLimit;

    @Column(name = "sold_count")
    @Builder.Default
    private Integer soldCount = 0;

    @Version
    @Column(name = "version")
    @Builder.Default
    private Integer version = 0;

    public boolean isSoldOut() {
        return this.soldCount >= this.quantityLimit;
    }
}