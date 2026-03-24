package com.project.shopapp.domains.inventory.config;

import com.project.shopapp.domains.inventory.repository.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class HoldExpirationJob {

    private final ProductItemRepository itemRepository;

    @Scheduled(fixedRate = 60000) // Chạy mỗi phút 1 lần
    @SchedulerLock(name = "ReleaseExpiredImeiHoldTask", lockAtMostFor = "50s", lockAtLeastFor = "10s")
    @Transactional
    public void releaseExpiredHolds() {
        log.debug("Đang quét để nhả các IMEI bị giữ quá hạn...");
        int releasedCount = itemRepository.releaseExpiredHolds(LocalDateTime.now());
        if (releasedCount > 0) {
            log.info("Đã giải phóng {} IMEI hết hạn giữ chỗ.", releasedCount);
            // Cần Sync lại tồn kho tổng (quantity) ở bảng Product / Variant thông qua Event nếu cần thiết.
        }
    }
}