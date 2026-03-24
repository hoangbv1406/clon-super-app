// --- api/PaymentInternalApi.java ---
package com.project.shopapp.domains.finance.api;
import java.math.BigDecimal;

public interface PaymentInternalApi {
    // Gọi khi OrderService vừa tạo xong Order, cần sinh Link thanh toán
    String initPaymentForOrder(Long orderId, BigDecimal amount, String method);

    // Gọi khi Admin duyệt trả hàng
    boolean processRefund(Long orderId, Integer orderShopId, BigDecimal refundAmount, String reason);
}