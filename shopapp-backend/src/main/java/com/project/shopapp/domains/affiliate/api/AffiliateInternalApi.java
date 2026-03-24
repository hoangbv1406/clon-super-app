// --- api/AffiliateInternalApi.java ---
package com.project.shopapp.domains.affiliate.api;
import java.math.BigDecimal;

public interface AffiliateInternalApi {
    // Gọi khi OrderService đang tính tiền để lấy Commission Rate gán vào đơn hàng (nếu có)
    BigDecimal getCustomCommissionRate(String code);

    // Gọi khi OrderShop chuyển trạng thái DELIVERED để tính tiền thưởng cho KOC
    void processAffiliateReward(String code, Long orderId, BigDecimal orderSubTotal);
}