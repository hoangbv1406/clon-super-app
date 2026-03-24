package com.project.shopapp.domains.inventory.repository;

import com.project.shopapp.domains.inventory.entity.ProductItem;
import com.project.shopapp.domains.inventory.enums.ItemStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Tớ đã thêm cái này
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Integer>, JpaSpecificationExecutor<ProductItem> {

    Optional<ProductItem> findByImeiCodeAndIsDeleted(String imeiCode, Long isDeleted);

    // Tớ đã thêm hàm này
    List<ProductItem> findByOrderId(Long orderId);

    // [1] Tìm N máy ĐANG SẴN SÀNG cho một Variant (Phục vụ khách hàng ấn Add to Cart)
    @Query("SELECT p FROM ProductItem p WHERE p.variantId = :variantId AND p.status = 'AVAILABLE' AND p.isDeleted = 0")
    List<ProductItem> findAvailableItemsForVariant(@Param("variantId") Integer variantId, Pageable pageable); // Đã thêm @Param

    // [2] Khóa IMEI cho Order (Optimistic Locking cực mạnh)
    @Modifying
    @Query("UPDATE ProductItem p SET p.status = 'HOLD', p.lockedUntil = :lockedUntil, p.orderId = :orderId, p.version = p.version + 1 " +
            "WHERE p.id IN :itemIds AND p.status = 'AVAILABLE' AND p.isDeleted = 0")
    int lockItems(@Param("itemIds") List<Integer> itemIds, @Param("orderId") Long orderId, @Param("lockedUntil") LocalDateTime lockedUntil); // Đã thêm @Param

    // [3] Mở khóa IMEI bị Time-out (CronJob sẽ gọi hàm này)
    @Modifying
    @Query("UPDATE ProductItem p SET p.status = 'AVAILABLE', p.lockedUntil = NULL, p.orderId = NULL, p.version = p.version + 1 " +
            "WHERE p.status = 'HOLD' AND p.lockedUntil < :now")
    int releaseExpiredHolds(@Param("now") LocalDateTime now); // Đã thêm @Param

    // [4] Bán đứt (Thanh toán xong)
    @Modifying
    @Query("UPDATE ProductItem p SET p.status = 'SOLD', p.soldDate = :now, p.lockedUntil = NULL, p.version = p.version + 1 " +
            "WHERE p.orderId = :orderId")
    int markAsSoldByOrder(@Param("orderId") Long orderId, @Param("now") LocalDateTime now); // Đã thêm @Param
}
