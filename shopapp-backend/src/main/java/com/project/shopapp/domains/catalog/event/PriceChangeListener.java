// --- event/listener/PriceChangeListener.java ---
package com.project.shopapp.domains.catalog.event.listener;

import com.project.shopapp.domains.catalog.entity.PriceHistory;
import com.project.shopapp.domains.catalog.enums.PriceType;
import com.project.shopapp.domains.catalog.event.ProductPriceChangedEvent;
import com.project.shopapp.domains.catalog.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceChangeListener {

    private final PriceHistoryRepository priceHistoryRepo;

    @Async("catalogEventTaskExecutor") // Chạy trên ThreadPool riêng biệt
    @EventListener
    @Transactional
    public void handleProductPriceChanged(ProductPriceChangedEvent event) {
        log.info("Lắng nghe sự kiện đổi giá cho Product {}. Đang ghi vào Price History...", event.getProductId());

        try {
            PriceHistory history = PriceHistory.builder()
                    .productId(event.getProductId())
                    .variantId(event.getVariantId()) // Nullable
                    .oldPrice(event.getOldPrice())
                    .newPrice(event.getNewPrice())
                    .priceType(PriceType.SELLING_PRICE)
                    .reason(event.getReason())
                    .updatedBy(event.getUpdatedBy())
                    .build();

            priceHistoryRepo.save(history);

            // TODO: Ở đây có thể Publish thêm Event "PriceDropAlertEvent" cho Module Marketing
        } catch (Exception e) {
            log.error("Lỗi khi ghi Lịch sử giá: {}", e.getMessage());
        }
    }
}