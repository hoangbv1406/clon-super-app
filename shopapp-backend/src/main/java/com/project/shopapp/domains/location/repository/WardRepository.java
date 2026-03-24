package com.project.shopapp.domains.location.repository;

import com.project.shopapp.domains.location.entity.Ward;
import com.project.shopapp.domains.location.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, String>, JpaSpecificationExecutor<Ward> {
    List<Ward> findByDistrictCodeAndIsActiveTrue(String districtCode);

    Optional<Ward> findByCodeAndIsActiveTrue(String code);

    // Dùng cho Bulk Update khi Admin muốn khóa giao hàng toàn bộ 1 huyện
    @Modifying
    @Query("UPDATE Ward w SET w.deliveryStatus = :status WHERE w.districtCode = :districtCode")
    int updateDeliveryStatusByDistrict(String districtCode, DeliveryStatus status);
}