package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    Optional<Product> findBySlugAndIsDeleted(String slug, Long isDeleted);

    boolean existsBySlugAndIsDeleted(String slug, Long isDeleted);

    // [QUAN TRỌNG NHẤT]: Cố định (Lock) tồn kho khi khách cho vào Giỏ / Checkout
    @Modifying
    @Query("UPDATE Product p SET p.reservedQuantity = p.reservedQuantity + :qty, p.version = p.version + 1 " +
            "WHERE p.id = :productId AND (p.quantity - p.reservedQuantity) >= :qty AND p.isDeleted = 0")
    int lockStock(Integer productId, Integer qty);

    // Mở khóa tồn kho (Khách hủy đơn hoặc quá 15 phút không thanh toán)
    @Modifying
    @Query("UPDATE Product p SET p.reservedQuantity = p.reservedQuantity - :qty, p.version = p.version + 1 " +
            "WHERE p.id = :productId AND p.reservedQuantity >= :qty")
    int unlockStock(Integer productId, Integer qty);

    // Khách thanh toán thành công: Trừ hẳn quantity, trừ reserved, cộng total_sold
    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :qty, p.reservedQuantity = p.reservedQuantity - :qty, " +
            "p.totalSold = p.totalSold + :qty, p.version = p.version + 1 " +
            "WHERE p.id = :productId")
    int commitStock(Integer productId, Integer qty);
}