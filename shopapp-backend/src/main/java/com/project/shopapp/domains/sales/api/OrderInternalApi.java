// --- api/OrderInternalApi.java ---
package com.project.shopapp.domains.sales.api;

public interface OrderInternalApi {
    void markOrderAsPaid(String orderCode);
    void markOrderAsPaymentFailed(String orderCode);
}