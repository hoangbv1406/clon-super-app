package com.project.shopapp.domains.location.entity;

import com.project.shopapp.domains.location.enums.DistrictType;
import com.project.shopapp.shared.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "districts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class District extends BaseAuditEntity implements Persistable<String> {

    @Id
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    // Trick chống N+1: Map field thô để xử lý logic nhanh
    @Column(name = "province_code", nullable = false)
    private String provinceCode;

    // Map Object dạng Lazy, chỉ read-only.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code", referencedColumnName = "code", insertable = false, updatable = false)
    private Province province;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Builder.Default
    private DistrictType type = DistrictType.UNKNOWN;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() { return code; }

    @Override
    public boolean isNew() { return isNew || getCreatedAt() == null; }

    @PrePersist
    @PostLoad
    void markNotNew() { this.isNew = false; }
}