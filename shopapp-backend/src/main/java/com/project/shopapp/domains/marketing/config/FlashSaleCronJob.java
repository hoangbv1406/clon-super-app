package com.project.shopapp.domains.marketing.config;

import com.project.shopapp.domains.marketing.entity.FlashSale;
import com.project.shopapp.domains.marketing.enums.FlashSaleStatus;
import com.project.shopapp.domains.marketing.event.FlashSaleStatusTransitionEvent;
import com.project.shopapp.domains.marketing.repository.FlashSaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlashSaleCronJob {

    private final FlashSaleRepository flashSaleRepo;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Chạy mỗi phút 1 lần để chuyển trạng thái Flash Sale.
     * ShedLock đảm bảo nếu có nhiều instance Backend, chỉ 1 máy được chạy tác vụ này.
     */
    @Scheduled(cron = "0 * * * * *") // Chạy ở giây 00 của mỗi phút
    @SchedulerLock(name = "FlashSaleStatusTransitionTask", lockAtMostFor = "50s", lockAtLeastFor = "10s")
    @Transactional
    public void transitionFlashSaleStatuses() {
        LocalDateTime now = LocalDateTime.now();
        log.debug("Bắt đầu quy trình kiểm tra Trạng thái Flash Sale lúc: {}", now);

        // 1. PENDING -> ACTIVE
        List<FlashSale> toActivate = flashSaleRepo.findPendingSalesToActivate(now);
        if (!toActivate.isEmpty()) {
            List<Integer> ids = toActivate.stream().map(FlashSale::getId).collect(Collectors.toList());
            flashSaleRepo.updateStatusBatch(ids, FlashSaleStatus.ACTIVE);

            for (Integer id : ids) {
                eventPublisher.publishEvent(new FlashSaleStatusTransitionEvent(id, FlashSaleStatus.PENDING, FlashSaleStatus.ACTIVE));
            }
            log.info("Đã kích hoạt (ACTIVE) {} chiến dịch Flash Sale.", ids.size());
        }

        // 2. ACTIVE -> ENDED
        List<FlashSale> toEnd = flashSaleRepo.findActiveSalesToEnd(now);
        if (!toEnd.isEmpty()) {
            List<Integer> ids = toEnd.stream().map(FlashSale::getId).collect(Collectors.toList());
            flashSaleRepo.updateStatusBatch(ids, FlashSaleStatus.ENDED);

            for (Integer id : ids) {
                eventPublisher.publishEvent(new FlashSaleStatusTransitionEvent(id, FlashSaleStatus.ACTIVE, FlashSaleStatus.ENDED));
            }
            log.info("Đã kết thúc (ENDED) {} chiến dịch Flash Sale.", ids.size());
        }
    }
}