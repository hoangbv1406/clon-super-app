package com.project.shopapp.domains.pos.api;

public interface PosSessionInternalApi {
    /**
     * Module Sales gọi hàm này để lấy ID của ca đang mở. Ném exception nếu chưa mở ca.
     */
    Integer getActiveSessionIdForUser(Integer shopId, Integer userId);

    /**
     * Tính tổng doanh thu tiền mặt của 1 ca (Module Sales sẽ cung cấp dữ liệu này cho POS)
     * // Note: Sẽ gọi ngược từ POS sang Sales
     */
}