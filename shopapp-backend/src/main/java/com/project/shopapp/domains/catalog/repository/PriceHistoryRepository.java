package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.PriceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer>, JpaSpecificationExecutor<PriceHistory> {

    // Phục vụ vẽ biểu đồ: Lấy tất cả lịch sử của 1 Variant sắp xếp theo thời gian
    List<PriceHistory> findByProductIdAndVariantIdOrderByCreatedAtAsc(Integer productId, Integer variantId);

    // Lịch sử chung của 1 Product (Dành cho Product không có Variant)
    List<PriceHistory> findByProductIdAndVariantIdIsNullOrderByCreatedAtAsc(Integer productId);
}