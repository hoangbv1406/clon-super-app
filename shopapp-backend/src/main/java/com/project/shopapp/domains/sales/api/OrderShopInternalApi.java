// --- api/OrderShopInternalApi.java ---
package com.project.shopapp.domains.sales.api;
import java.util.List;

public interface OrderShopInternalApi {
    /**
     * Tự động tách CartItems thành các OrderShop tương ứng.
     * Trả về danh sách OrderShop ID vừa tạo.
     */
    List<Integer> splitOrderToShops(Long parentOrderId, Integer userId);
}