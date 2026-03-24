package com.project.shopapp.domains.identity.config;

import com.project.shopapp.domains.identity.constant.SessionConstants;
import com.project.shopapp.domains.identity.repository.UserSessionRepository;
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
public class SessionCleanupConfig {

    private final UserSessionRepository sessionRepository;

    @Scheduled(cron = SessionConstants.CRON_CLEAN_EXPIRED_SESSIONS)
    @SchedulerLock(
            name = SessionConstants.SHEDLOCK_CLEAN_SESSIONS,
            lockAtLeastFor = "5m",
            lockAtMostFor = "15m"
    )
    @Transactional
    public void cleanExpiredSessions() {
        log.info("Bắt đầu dọn dẹp các User Session đã hết hạn...");
        int deletedRows = sessionRepository.deleteAllExpiredSince(LocalDateTime.now());
        log.info("Đã xóa {} sessions rác khỏi Database.", deletedRows);
    }
}
