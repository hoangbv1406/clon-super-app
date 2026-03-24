package com.project.shopapp.domains.catalog.entity;

import com.project.shopapp.domains.catalog.enums.CategoryDisplayMode;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Category extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    // Self-referencing (Load con của danh mục này)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("displayOrder ASC")
    private List<Category> children;

    @Column(name = "path", length = 255)
    private String path; // VD: "/1/5/12/"

    @Column(name = "level")
    @Builder.Default
    private Integer level = 1;

    @Column(name = "slug", length = 100)
    private String slug;

    @Column(name = "icon_url", length = 255)
    private String iconUrl;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "display_mode")
    @Builder.Default
    private CategoryDisplayMode displayMode = CategoryDisplayMode.SHOW_ON_MENU;
}