package com.project.shopapp.domains.sales.repository;

import com.project.shopapp.domains.sales.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>, JpaSpecificationExecutor<CartItem> {

    List<CartItem> findByCartId(Integer cartId);

    Optional<CartItem> findByCartIdAndProductIdAndVariantId(Integer cartId, Integer productId, Integer variantId);

    // Cộng dồn an toàn
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = c.quantity + :qty WHERE c.id = :itemId")
    void incrementQuantity(Integer itemId, int qty);

    // Xóa toàn bộ item của giỏ (Sau khi thanh toán thành công)
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cartId = :cartId")
    void deleteByCartId(Integer cartId);

    // Select/Deselect tất cả
    @Modifying
    @Query("UPDATE CartItem c SET c.isSelected = :isSelected WHERE c.cartId = :cartId")
    void updateSelectAll(Integer cartId, boolean isSelected);

    // Xóa các item đã Select (Dùng khi checkout thành công nhưng giỏ vẫn còn đồ chưa select)
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cartId = :cartId AND c.isSelected = true")
    void deleteSelectedItems(Integer cartId);
}