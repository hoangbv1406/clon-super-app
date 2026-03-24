// --- api/UserFollowInternalApi.java ---
package com.project.shopapp.domains.social.api;

public interface UserFollowInternalApi {
    // Trả về True nếu Khách hàng ĐANG THEO DÕI Shop này (Dùng để duyệt điều kiện xài Coupon)
    boolean isUserFollowingShop(Integer userId, Integer shopId);

    // Dùng để hiển thị thống kê lên gian hàng của Shop
    long getShopFollowerCount(Integer shopId);
}