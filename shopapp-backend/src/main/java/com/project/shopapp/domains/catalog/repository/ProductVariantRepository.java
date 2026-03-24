package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer>, JpaSpecificationExecutor<ProductVariant> {

    boolean existsBySkuAndIsDeleted(String sku, Long isDeleted);

    List<ProductVariant> findByProductIdAndIsDeleted(Integer productId, Long isDeleted);

    // 1. Khách bỏ vào giỏ và ấn "Thanh toán": Giữ kho (Lock Stock)
    @Modifying
    @Query("UPDATE ProductVariant v SET v.reservedQuantity = v.reservedQuantity + :qty, v.version = v.version + 1 " +
            "WHERE v.id = :variantId AND (v.quantity - v.reservedQuantity) >= :qty AND v.isDeleted = 0 AND v.isActive = true")
    int lockVariantStock(Integer variantId, Integer qty);

    // 2. Khách hủy đơn / Hết 15p không trả tiền: Nhả kho (Unlock Stock)
    @Modifying
    @Query("UPDATE ProductVariant v SET v.reservedQuantity = v.reservedQuantity - :qty, v.version = v.version + 1 " +
            "WHERE v.id = :variantId AND v.reservedQuantity >= :qty")
    int unlockVariantStock(Integer variantId, Integer qty);

    // 3. Khách thanh toán thành công: Trừ hẳn kho (Commit Stock)
    @Modifying
    @Query("UPDATE ProductVariant v SET v.quantity = v.quantity - :qty, v.reservedQuantity = v.reservedQuantity - :qty, " +
            "v.version = v.version + 1 WHERE v.id = :variantId")
    int commitVariantStock(Integer variantId, Integer qty);
}