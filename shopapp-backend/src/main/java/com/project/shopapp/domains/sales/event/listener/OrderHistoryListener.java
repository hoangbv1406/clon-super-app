package com.project.shopapp.domains.sales.event.listener;

import com.project.shopapp.domains.sales.entity.OrderHistory;
import com.project.shopapp.domains.sales.event.OrderStatusChangedEvent;
import com.project.shopapp.domains.sales.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderHistoryListener {

    private final OrderHistoryRepository historyRepo;

    @Async("salesEventTaskExecutor") // Chạy ngầm trên ThreadPool riêng
    @EventListener
    @Transactional
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        log.info("Ghi nhận lịch sử: Đơn hàng {} -> Trạng thái: {}", event.getOrderId(), event.getNewStatus());

        try {
            OrderHistory history = OrderHistory.builder()
                    .orderId(event.getOrderId())
                    .orderShopId(event.getOrderShopId())
                    .status(event.getNewStatus())
                    .note(event.getNote())
                    .updatedBy(event.getUpdatedBy())
                    .build();

            historyRepo.save(history);
        } catch (Exception e) {
            log.error("Lỗi khi ghi Lịch sử Đơn hàng: {}", e.getMessage(), e);
        }
    }
}