package com.project.shopapp.domains.catalog.entity;

import com.project.shopapp.domains.catalog.enums.BrandTier;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Brand extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "slug", length = 100)
    private String slug;

    @Column(name = "icon_url", length = 255)
    private String iconUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "tier")
    @Builder.Default
    private BrandTier tier = BrandTier.REGULAR;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}