// --- service/OrderShopService.java ---
package com.project.shopapp.domains.sales.service;
import com.project.shopapp.domains.sales.dto.request.OrderShopFulfillmentRequest;
import com.project.shopapp.domains.sales.dto.response.OrderShopResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface OrderShopService {
    PageResponse<OrderShopResponse> getVendorOrders(Integer shopId, String status, String code, int page, int size);
    OrderShopResponse updateFulfillment(Integer userId, Integer shopId, Integer orderShopId, OrderShopFulfillmentRequest request);
    void markAsShipped(Integer userId, Integer shopId, Integer orderShopId);
    void markAsDelivered(Integer userId, Integer shopId, Integer orderShopId);
}