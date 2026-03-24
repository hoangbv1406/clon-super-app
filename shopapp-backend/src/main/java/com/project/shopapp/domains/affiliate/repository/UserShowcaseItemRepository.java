package com.project.shopapp.domains.affiliate.repository;

import com.project.shopapp.domains.affiliate.entity.UserShowcaseItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserShowcaseItemRepository extends JpaRepository<UserShowcaseItem, Integer> {

    // Check xem sản phẩm đã có trong tủ chưa
    boolean existsByUserIdAndProductIdAndIsDeleted(Integer userId, Integer productId, Long isDeleted);

    // Đếm số lượng sản phẩm trong tủ
    long countByUserIdAndIsDeleted(Integer userId, Long isDeleted);

    // Tìm chi tiết 1 item của 1 KOC
    Optional<UserShowcaseItem> findByIdAndUserIdAndIsDeleted(Integer id, Integer userId, Long isDeleted);

    // Lấy tủ đồ PUBLIC cho Fan xem (Bỏ qua những món bị is_hidden = true)
    // Sắp xếp ưu tiên: displayOrder (asc) -> createdAt (desc)
    Page<UserShowcaseItem> findByUserIdAndIsHiddenFalseAndIsDeletedOrderByDisplayOrderAscCreatedAtDesc(Integer userId, Long isDeleted, Pageable pageable);

    // Lấy tủ đồ PRIVATE cho KOC xem (Để họ quản lý bật/tắt hiển thị)
    Page<UserShowcaseItem> findByUserIdAndIsDeletedOrderByDisplayOrderAscCreatedAtDesc(Integer userId, Long isDeleted, Pageable pageable);
}