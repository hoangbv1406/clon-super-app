package com.project.shopapp.domains.sales.config;

import com.project.shopapp.domains.sales.repository.CartRepository;
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
public class CartJobsConfig {

    private final CartRepository cartRepository;

    /**
     * Dọn dẹp Giỏ hàng vãng lai hết hạn vào lúc 03:00 sáng mỗi ngày.
     */
    @Scheduled(cron = "0 0 3 * * *")
    @SchedulerLock(name = "CleanupExpiredGuestCartsTask", lockAtMostFor = "10m", lockAtLeastFor = "1m")
    @Transactional
    public void cleanupExpiredGuestCarts() {
        log.info("Bắt đầu dọn dẹp giỏ hàng Guest hết hạn...");
        int deleted = cartRepository.softDeleteExpiredGuestCarts(LocalDateTime.now(), System.currentTimeMillis());
        if (deleted > 0) {
            log.info("Đã xóa mềm {} giỏ hàng vãng lai hết hạn.", deleted);
        }
    }
}