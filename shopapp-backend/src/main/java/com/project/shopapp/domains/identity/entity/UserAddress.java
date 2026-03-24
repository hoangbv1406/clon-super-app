package com.project.shopapp.domains.identity.entity;

import com.project.shopapp.domains.identity.enums.AddressType;
import com.project.shopapp.domains.location.entity.District;
import com.project.shopapp.domains.location.entity.Province;
import com.project.shopapp.domains.location.entity.Ward;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "recipient_name", length = 100)
    private String recipientName;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "address_detail", length = 200, nullable = false)
    private String addressDetail;

    // --- Trick Chống N+1 ---
    @Column(name = "province_code", length = 20)
    private String provinceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code", insertable = false, updatable = false)
    private Province province;

    @Column(name = "district_code", length = 20)
    private String districtCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_code", insertable = false, updatable = false)
    private District district;

    @Column(name = "ward_code", length = 20)
    private String wardCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_code", insertable = false, updatable = false)
    private Ward ward;
    // ------------------------

    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    @Builder.Default
    private AddressType addressType = AddressType.HOME;

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;
}