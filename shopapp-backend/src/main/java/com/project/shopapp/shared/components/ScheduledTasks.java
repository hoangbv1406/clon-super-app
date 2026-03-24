package com.project.shopapp.shared.components;

import com.project.shopapp.domains.identity.api.SessionInternalApi;
import com.project.shopapp.domains.inventory.api.ProductItemInternalApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {

    // Tiêm các API nội bộ thay vì gọi trực tiếp Repository
    private final SessionInternalApi sessionApi;
    private final ProductItemInternalApi productItemApi;

    @Scheduled(fixedRate = 600000) // Chạy mỗi 10 phút
    @SchedulerLock(name = "cleanupExpiredTokensLock", lockAtLeastFor = "5m", lockAtMostFor = "9m")
    public void cleanupExpiredTokens() {
        log.info("[CRON] Bắt đầu dọn dẹp các Session hết hạn...");
        try {
            sessionApi.cleanupExpiredSessions();
        } catch (Exception e) {
            log.error("[CRON] Lỗi khi dọn dẹp Session: {}", e.getMessage());
        }
    }

    @Scheduled(fixedRate = 60000) // Chạy mỗi 1 phút
    @SchedulerLock(name = "unlockProductItemsLock", lockAtLeastFor = "30s", lockAtMostFor = "50s")
    public void unlockProductItems() {
        log.info("[CRON] Kiểm tra và nhả tồn kho (Unlock Items) quá hạn...");
        try {
            productItemApi.releaseExpiredHoldItems();
        } catch (Exception e) {
            log.error("[CRON] Lỗi khi nhả tồn kho: {}", e.getMessage());
        }
    }
}
