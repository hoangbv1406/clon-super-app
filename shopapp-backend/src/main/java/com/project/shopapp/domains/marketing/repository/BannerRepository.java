package com.project.shopapp.domains.marketing.repository;

import com.project.shopapp.domains.marketing.entity.Banner;
import com.project.shopapp.domains.marketing.enums.BannerPlatform;
import com.project.shopapp.domains.marketing.enums.BannerPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer>, JpaSpecificationExecutor<Banner> {

    // Tìm các Banner ĐANG CÓ HIỆU LỰC (Dành cho Trang chủ)
    @Query("SELECT b FROM Banner b WHERE b.isDeleted = 0 AND b.isActive = true " +
            "AND (b.startTime IS NULL OR b.startTime <= :now) " +
            "AND (b.endTime IS NULL OR b.endTime >= :now) " +
            "AND b.position = :position " +
            "AND (b.platform = 'ALL' OR b.platform = :platform) " +
            "ORDER BY b.displayOrder ASC")
    List<Banner> findActiveBanners(BannerPosition position, BannerPlatform platform, LocalDateTime now);

    // Tăng click count trực tiếp bằng Query để tránh Lock Update mất thời gian
    @Modifying
    @Query("UPDATE Banner b SET b.clickCount = b.clickCount + 1 WHERE b.id = :bannerId")
    void incrementClickCount(Integer bannerId);
}