package com.project.shopapp.domains.identity.api;

public interface UserCredentialInternalApi {
    /**
     * Trả về true nếu user có cài đặt Passkey/FaceID (để hiển thị nút "Xác thực sinh trắc học")
     */
    boolean userHasActivePasskeys(Integer userId);
}