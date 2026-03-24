package com.project.shopapp.domains.location.entity;

import com.project.shopapp.domains.location.enums.Region;
import com.project.shopapp.shared.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "provinces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Province extends BaseAuditEntity implements Persistable<String> {

    @Id
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    @Builder.Default
    private Region region = Region.UNKNOWN;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    // Custom logic cho khóa chính String để JPA không bị nhầm lẫn giữa Save và Update
    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return code;
    }

    @Override
    public boolean isNew() {
        return isNew || getCreatedAt() == null;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}