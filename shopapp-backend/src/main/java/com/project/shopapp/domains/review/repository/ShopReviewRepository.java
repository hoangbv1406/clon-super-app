package com.project.shopapp.domains.review.repository;

import com.project.shopapp.domains.review.entity.ShopReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopReviewRepository extends JpaRepository<ShopReview, Long>, JpaSpecificationExecutor<ShopReview> {

    boolean existsByUserIdAndOrderShopIdAndIsDeleted(Integer userId, Integer orderShopId, Long isDeleted);

    // Lấy các review GỐC (parent_id is null) của một Shop, phân trang
    Page<ShopReview> findByShopIdAndParentIdIsNullAndIsDeleted(Integer shopId, Long isDeleted, Pageable pageable);

    // Lấy câu trả lời (Reply) của Shop cho một review cụ thể
    Optional<ShopReview> findByParentIdAndIsDeleted(Long parentId, Long isDeleted);
}