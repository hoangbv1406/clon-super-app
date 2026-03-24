// --- api/OrderDetailInternalApi.java ---
package com.project.shopapp.domains.sales.api;

public interface OrderDetailInternalApi {
    /**
     * Build và Save các OrderDetails vào Database dựa trên Giỏ hàng.
     * Hàm này được OrderShopInternalApi (hoặc OrderService) gọi khi Checkout thành công.
     */
    void buildAndSaveDetailsForOrder(Long parentOrderId, Integer cartId);
}