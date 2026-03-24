package com.project.shopapp.domains.marketing.repository;

import com.project.shopapp.domains.marketing.entity.FlashSaleItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlashSaleItemRepository extends JpaRepository<FlashSaleItem, Long>, JpaSpecificationExecutor<FlashSaleItem> {

    Page<FlashSaleItem> findByFlashSaleIdAndIsDeleted(Integer flashSaleId, Long isDeleted, Pageable pageable);

    boolean existsByFlashSaleIdAndProductIdAndVariantIdAndIsDeleted(Integer flashSaleId, Integer productId, Integer variantId, Long isDeleted);

    // Dành cho việc hiển thị Badge ở trang Product Detail
    @Query("SELECT f FROM FlashSaleItem f JOIN f.flashSale fs " +
            "WHERE f.productId = :productId AND f.variantId = :variantId AND f.isDeleted = 0 " +
            "AND fs.status = 'ACTIVE' AND fs.isDeleted = 0")
    Optional<FlashSaleItem> findActiveSaleForProduct(Integer productId, Integer variantId);

    // [TỐI QUAN TRỌNG]: Tăng sold_count nguyên tử.
    // Điều kiện: Chỉ update nếu (quantityLimit - soldCount) >= qty (Chống bán âm)
    @Modifying
    @Query("UPDATE FlashSaleItem f SET f.soldCount = f.soldCount + :qty, f.version = f.version + 1 " +
            "WHERE f.id = :itemId AND (f.quantityLimit - f.soldCount) >= :qty AND f.isDeleted = 0")
    int consumeStock(Long itemId, int qty);

    // Hàm trả lại kho (Nếu khách hủy đơn Flash Sale)
    @Modifying
    @Query("UPDATE FlashSaleItem f SET f.soldCount = f.soldCount - :qty, f.version = f.version + 1 " +
            "WHERE f.id = :itemId AND f.soldCount >= :qty")
    int releaseStock(Long itemId, int qty);
}