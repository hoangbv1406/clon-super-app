// --- api/NotificationInternalApi.java ---
package com.project.shopapp.domains.notification.api;

public interface NotificationInternalApi {
    void pushOrderUpdate(Integer userId, String orderCode, String statusVi, String deepLink);
    void pushSystemAlert(Integer userId, String title, String body);
    void pushPromotion(Integer userId, String title, String body, String imageUrl, String deepLink);
}