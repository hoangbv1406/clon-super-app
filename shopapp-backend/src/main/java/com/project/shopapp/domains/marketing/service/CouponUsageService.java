// --- service/CouponUsageService.java ---
package com.project.shopapp.domains.marketing.service;
import com.project.shopapp.domains.marketing.dto.response.CouponUsageResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface CouponUsageService {
    PageResponse<CouponUsageResponse> getUsagesForAdmin(Integer couponId, Integer userId, String status, int page, int size);
}