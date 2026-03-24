package com.project.shopapp.domains.catalog.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "variant_values")
@IdClass(VariantValueId.class) // Đánh dấu Composite Key
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantValue {

    @Id
    @Column(name = "variant_id", nullable = false)
    private Integer variantId;

    @Id
    @Column(name = "option_id", nullable = false)
    private Integer optionId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "option_value_id", nullable = false)
    private Integer optionValueId;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- Các Lazy Join để dùng khi cần Report ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_value_id", insertable = false, updatable = false)
    private OptionValue optionValue;
}