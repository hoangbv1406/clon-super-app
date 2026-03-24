package com.project.shopapp.domains.marketing.event.listener;

import com.project.shopapp.domains.marketing.api.CouponUsageInternalApi;
// Import event từ module sales (Giả định tớ đã định nghĩa nó ở bảng orders)
import com.project.shopapp.domains.sales.event.OrderCancelledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCancelledMarketingListener {

    private final CouponUsageInternalApi usageApi;

    // Lắng nghe Event từ bảng Order
    @Async("marketingTaskExecutor")
    @EventListener
    @Transactional
    public void handleOrderCancelled(OrderCancelledEvent event) {
        log.info("Lắng nghe lệnh Hủy Đơn Hàng {}. Tiến hành hoàn trả Voucher (nếu có)...", event.getOrderId());
        try {
            usageApi.revertCouponUsageForOrder(event.getOrderId());
        } catch (Exception e) {
            log.error("Lỗi khi hoàn trả Voucher cho đơn {}: {}", event.getOrderId(), e.getMessage());
        }
    }
}