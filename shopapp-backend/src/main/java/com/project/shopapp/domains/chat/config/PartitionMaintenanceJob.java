package com.project.shopapp.domains.chat.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PartitionMaintenanceJob {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Chạy tự động vào ngày 25 hàng tháng.
     * Nó sẽ gửi lệnh ALTER TABLE để tạo Partition cho tháng tiếp theo.
     */
    @Scheduled(cron = "0 0 2 25 * *")
    @SchedulerLock(name = "MaintainChatPartitionsTask", lockAtMostFor = "10m", lockAtLeastFor = "1m")
    public void maintainPartitions() {
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        LocalDate nextNextMonth = nextMonth.plusMonths(1);

        String partitionName = "p" + nextMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String limitValue = nextNextMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));

        log.info("Chuẩn bị tạo Partition {} (VALUES LESS THAN {}) cho bảng chat_messages", partitionName, limitValue);

        try {
            // Lệnh REORGANIZE chia tách PARTITION p_max thành partition của tháng mới và p_max mới.
            // (Phải dùng REORGANIZE vì schema cậu có p_max VALUES LESS THAN MAXVALUE)
            String sql = String.format(
                    "ALTER TABLE chat_messages REORGANIZE PARTITION p_max INTO (" +
                            "PARTITION %s VALUES LESS THAN (%s) ENGINE = InnoDB, " +
                            "PARTITION p_max VALUES LESS THAN MAXVALUE ENGINE = InnoDB" +
                            ")", partitionName, limitValue);

            jdbcTemplate.execute(sql);
            log.info("Bảo trì Partition Database thành công!");
        } catch (Exception e) {
            log.error("Lỗi khi tạo Partition Database: {}", e.getMessage());
            // TODO: Bắn cảnh báo cho đội DevOps / DBA qua Slack/Telegram ngay lập tức!
        }
    }
}