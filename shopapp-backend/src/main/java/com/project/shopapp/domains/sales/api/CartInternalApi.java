// --- api/CartInternalApi.java ---
package com.project.shopapp.domains.sales.api;

public interface CartInternalApi {
    // Xin cấp/lấy ID Giỏ hàng dựa trên định danh (Chỉ trả về nếu Cart ACTIVE)
    Integer getOrProvisionCartId(Integer userId, String sessionId);

    // Module Checkout gọi để Khóa giỏ
    void lockCart(Integer cartId);

    // Module Checkout gọi để Mở khóa giỏ (nếu khách thanh toán thất bại / hủy)
    void unlockCart(Integer cartId);
}