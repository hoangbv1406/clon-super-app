// --- api/AffiliateTransInternalApi.java ---
package com.project.shopapp.domains.affiliate.api;
import java.math.BigDecimal;

public interface AffiliateTransInternalApi {
    void recordPendingCommission(Integer affiliateLinkId, Integer orderShopId, BigDecimal commissionAmount);
    void reverseCommissionDueToReturn(Integer orderShopId);
    void cancelCommissionDueToFailedDelivery(Integer orderShopId);
}
