package com.project.shopapp.domains.community.repository;

import com.project.shopapp.domains.community.entity.ProductReview;
import com.project.shopapp.domains.community.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer>, JpaSpecificationExecutor<ProductReview> {

    // Đảm bảo Order Detail này chưa từng được review
    boolean existsByOrderDetailIdAndIsDeleted(Integer orderDetailId, Long isDeleted);

    // Lấy Reply của Shop cho 1 review cụ thể
    Optional<ProductReview> findByParentIdAndIsDeleted(Integer parentId, Long isDeleted);

    // Tăng lượt hữu ích
    @Modifying
    @Query("UPDATE ProductReview r SET r.helpfulCount = r.helpfulCount + 1 WHERE r.id = :reviewId AND r.isDeleted = 0")
    void incrementHelpfulCount(Integer reviewId);

    // TÍNH TOÁN NGAY LẬP TỨC TRUNG BÌNH SAO VÀ TỔNG SỐ ĐỂ BẮN EVENT SANG PRODUCT
    @Query("SELECT COUNT(r) FROM ProductReview r WHERE r.productId = :productId AND r.status = 'APPROVED' AND r.parentId IS NULL AND r.isDeleted = 0")
    int countApprovedReviewsByProduct(Integer productId);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM ProductReview r WHERE r.productId = :productId AND r.status = 'APPROVED' AND r.parentId IS NULL AND r.isDeleted = 0")
    float getAverageRatingByProduct(Integer productId);
}