package com.project.shopapp.domains.identity.api.impl;

import com.project.shopapp.domains.identity.api.SessionInternalApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j // Dùng cái này để in log ra console cho ngầu nhé
@Service // Lá bùa hộ mệnh để Spring Boot nhận diện đây rồi!
public class SessionInternalApiImpl implements SessionInternalApi {

    @Override
    public void forceLogoutAllDevices(Integer userId) {
        // Tạm thời tớ viết in log để app khởi động lên mượt mà đã.
        // TODO: Về sau cậu tiêm (inject) UserSessionRepository vào đây,
        // gọi hàm update cột is_revoked = 1 cho tất cả session của userId này nhé.
        log.info("Đã nhận lệnh force logout tất cả thiết bị của user có ID: {}", userId);
    }

    @Override
    public void cleanupExpiredSessions() {
        // Hàm này chắc chắn đang được thằng ScheduledTasks gọi định kỳ này.
        // TODO: Về sau cậu viết query xóa các session có cột expires_at < thời gian hiện tại nhé.
        log.info("Đang thực thi job dọn dẹp các session đã hết hạn trong database...");
    }
}