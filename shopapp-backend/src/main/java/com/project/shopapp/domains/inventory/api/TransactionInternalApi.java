package com.project.shopapp.domains.inventory.api;

public interface TransactionInternalApi {
    /**
     * Tự động tạo phiếu xuất kho khi đơn hàng hoàn tất.
     * @return Transaction ID
     */
    Long autoCreateOutboundForOrder(Integer shopId, Long orderId, String note);
}