package com.project.shopapp.domains.location.api;

public interface DeliveryZoneFacade {
    /**
     * Trả về true nếu Phường/Xã đang ở trạng thái AVAILABLE
     * Được gọi bởi OrderService khi khách ấn nút "Đặt hàng"
     */
    boolean isDeliverable(String wardCode);
}