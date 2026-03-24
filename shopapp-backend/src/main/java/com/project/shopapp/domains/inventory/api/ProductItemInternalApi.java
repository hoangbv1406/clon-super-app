package com.project.shopapp.domains.inventory.api;
import java.util.List;

public interface ProductItemInternalApi {
    /**
     * Cấp phát và Khóa các IMEI khả dụng cho một Order.
     * @return Danh sách ID các Item đã khóa. Ném Exception nếu không đủ hàng.
     */
    List<Integer> lockAvailableItemsForOrder(Integer variantId, int quantity, Long orderId);

    /**
     * Xác nhận đơn hàng thành công, chuyển sang SOLD.
     */
    void confirmSoldByOrder(Long orderId);
    void releaseExpiredHoldItems();
}