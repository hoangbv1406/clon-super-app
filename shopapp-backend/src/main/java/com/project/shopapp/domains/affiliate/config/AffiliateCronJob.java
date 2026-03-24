package com.project.shopapp.domains.affiliate.config;

import com.project.shopapp.domains.affiliate.constant.AffiliateTransConstants;
import com.project.shopapp.domains.affiliate.entity.AffiliateTransaction;
import com.project.shopapp.domains.affiliate.enums.AffiliateTransStatus;
import com.project.shopapp.domains.affiliate.event.AffiliateCommissionApprovedEvent;
import com.project.shopapp.domains.affiliate.repository.AffiliateTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AffiliateCronJob {

    private final AffiliateTransactionRepository transRepo;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Chạy mỗi đêm lúc 01:00 AM để quét các giao dịch PENDING đã an toàn (Qua thời gian đổi trả).
     */
    @Scheduled(cron = "0 0 1 * * *")
    @SchedulerLock(name = "AutoApproveCommissionTask", lockAtMostFor = "20m", lockAtLeastFor = "1m")
    @Transactional
    public void autoApproveCommissions() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(AffiliateTransConstants.DAYS_TO_AUTO_APPROVE);
        log.info("Bắt đầu quét duyệt Tự động Hoa hồng Tiếp thị từ ngày {} trở về trước...", threshold);

        List<AffiliateTransaction> pendingList = transRepo.findTransactionsToAutoApprove(threshold);

        if (!pendingList.isEmpty()) {
            for (AffiliateTransaction tx : pendingList) {
                tx.setStatus(AffiliateTransStatus.APPROVED);
                transRepo.save(tx);

                // Bắn Event để Module Notification gửi thông báo cho KOC: "Hoa hồng của bạn đã được chốt!"
                eventPublisher.publishEvent(new AffiliateCommissionApprovedEvent(tx.getAffiliateLink().getUserId(), tx.getAmount()));
            }
            log.info("Đã duyệt tự động thành công {} giao dịch Hoa hồng.", pendingList.size());
        }
    }

    // TODO: Có thể viết thêm Job thứ 2 để TỰ ĐỘNG TRẢ TIỀN (PAID) vào Ví của KOC vào ngày mùng 10 hàng tháng.
}