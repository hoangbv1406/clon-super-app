package com.project.shopapp.domains.catalog.entity;

import com.project.shopapp.domains.catalog.enums.OptionType;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Option extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Builder.Default
    private OptionType type = OptionType.TEXT;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}