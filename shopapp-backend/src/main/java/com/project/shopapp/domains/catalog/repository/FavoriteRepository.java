package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    Optional<Favorite> findByUserIdAndProductIdAndIsDeleted(Integer userId, Integer productId, Long isDeleted);

    boolean existsByUserIdAndProductIdAndIsDeleted(Integer userId, Integer productId, Long isDeleted);

    // Lấy danh sách Wishlist của khách hàng (Mới nhất lên đầu)
    Page<Favorite> findByUserIdAndIsDeleted(Integer userId, Long isDeleted, Pageable pageable);

    // Kế toán/Marketing muốn biết món hàng này đang được bao nhiêu người yêu thích
    long countByProductIdAndIsDeleted(Integer productId, Long isDeleted);

    // Kịch bản Enterprise: Lấy danh sách User đã thích SP này để gửi Email khi SP giảm giá
    @Query("SELECT f.userId FROM Favorite f WHERE f.productId = :productId AND f.isDeleted = 0")
    List<Integer> findUserIdsByFavoritedProduct(Integer productId);

    // Ngăn 1 user lưu quá nhiều SP rác
    long countByUserIdAndIsDeleted(Integer userId, Long isDeleted);
}