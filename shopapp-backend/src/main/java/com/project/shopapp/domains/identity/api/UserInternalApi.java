package com.project.shopapp.domains.identity.api;

public interface UserInternalApi {
    /**
     * Lấy thông tin cơ bản của User (Có Cache) phục vụ cho các Module khác
     */
    String getUserFullName(Integer userId);
    boolean isUserActive(Integer userId);
}