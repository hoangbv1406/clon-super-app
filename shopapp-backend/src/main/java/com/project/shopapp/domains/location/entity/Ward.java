package com.project.shopapp.domains.location.entity;

import com.project.shopapp.domains.location.enums.DeliveryStatus;
import com.project.shopapp.shared.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "wards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Ward extends BaseAuditEntity implements Persistable<String> {

    @Id
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    // Chống N+1 Query
    @Column(name = "district_code", nullable = false)
    private String districtCode;

    // Read-only object để join khi thực sự cần (ví dụ xuất báo cáo)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_code", referencedColumnName = "code", insertable = false, updatable = false)
    private District district;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    @Builder.Default
    private DeliveryStatus deliveryStatus = DeliveryStatus.AVAILABLE;

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