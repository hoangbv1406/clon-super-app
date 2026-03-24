package com.project.shopapp.domains.identity.constant;

public final class SessionConstants {
    private SessionConstants() {}

    // Tiền tố lưu cache các Token bị cấm
    public static final String REDIS_BLACKLIST_PREFIX = "BLACKLIST_";

    // Thời gian sống mặc định của Refresh Token (Tính bằng ngày)
    public static final int REFRESH_TOKEN_EXPIRATION_DAYS = 7;

    // Job dọn dẹp chạy vào 2h sáng mỗi ngày
    public static final String CRON_CLEAN_EXPIRED_SESSIONS = "0 0 2 * * *";

    // Tên Lock để đồng bộ giữa các Pods trên K8s
    public static final String SHEDLOCK_CLEAN_SESSIONS = "cleanExpiredSessionsLock";
}