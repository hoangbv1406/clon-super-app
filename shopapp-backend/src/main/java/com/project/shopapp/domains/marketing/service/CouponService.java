package com.project.shopapp.domains.marketing.service;
import com.project.shopapp.domains.marketing.dto.request.CouponCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.CouponResponse;
import java.util.List;

public interface CouponService {
    List<CouponResponse> getAvailableCouponsForShop(Integer shopId);
    CouponResponse createCoupon(Integer currentUserId, Integer shopId, CouponCreateRequest request);
    void toggleCouponStatus(Integer currentUserId, Integer couponId, boolean isActive);
}