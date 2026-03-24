package com.project.shopapp.domains.vendor.repository;

import com.project.shopapp.domains.vendor.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer>, JpaSpecificationExecutor<Shop> {
    Optional<Shop> findBySlugAndIsDeleted(String slug, Long isDeleted);

    Optional<Shop> findByOwnerIdAndIsDeleted(Integer ownerId, Long isDeleted);

    boolean existsBySlugAndIsDeleted(String slug, Long isDeleted);

    // Tối ưu hóa: Dùng Modifying Query kết hợp Optimistic Locking version
    // để tăng order một cách an toàn nhất dưới DB
    @Modifying
    @Query("UPDATE Shop s SET s.totalOrders = s.totalOrders + 1, s.version = s.version + 1 WHERE s.id = :shopId")
    void incrementTotalOrders(Integer shopId);
}