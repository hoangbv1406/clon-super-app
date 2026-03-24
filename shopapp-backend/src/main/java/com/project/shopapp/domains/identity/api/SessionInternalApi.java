package com.project.shopapp.domains.identity.api;

public interface SessionInternalApi {
    void forceLogoutAllDevices(Integer userId);
    void cleanupExpiredSessions();
}