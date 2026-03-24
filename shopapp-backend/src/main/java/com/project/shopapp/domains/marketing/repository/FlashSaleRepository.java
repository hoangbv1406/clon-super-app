package com.project.shopapp.domains.marketing.repository;

import com.project.shopapp.domains.marketing.entity.FlashSale;
import com.project.shopapp.domains.marketing.enums.FlashSaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlashSaleRepository extends JpaRepository<FlashSale, Integer>, JpaSpecificationExecutor<FlashSale> {

    // Tìm các Flash Sale cần kích hoạt (PENDING -> ACTIVE)
    @Query("SELECT f FROM FlashSale f WHERE f.status = 'PENDING' AND f.startTime <= :now AND f.isDeleted = 0")
    List<FlashSale> findPendingSalesToActivate(LocalDateTime now);

    // Tìm các Flash Sale cần đóng (ACTIVE -> ENDED)
    @Query("SELECT f FROM FlashSale f WHERE f.status = 'ACTIVE' AND f.endTime <= :now AND f.isDeleted = 0")
    List<FlashSale> findActiveSalesToEnd(LocalDateTime now);

    // Batch Update Status tăng tốc
    @Modifying
    @Query("UPDATE FlashSale f SET f.status = :newStatus WHERE f.id IN :ids")
    void updateStatusBatch(List<Integer> ids, FlashSaleStatus newStatus);

    // Ngăn 1 shop tạo 2 đợt Flash Sale đè thời gian lên nhau
    @Query("SELECT COUNT(f) FROM FlashSale f WHERE f.shopId = :shopId AND f.isDeleted = 0 AND f.status != 'CANCELLED' " +
            "AND ((:start BETWEEN f.startTime AND f.endTime) OR (:end BETWEEN f.startTime AND f.endTime))")
    long countOverlappingSales(Integer shopId, LocalDateTime start, LocalDateTime end);
}