// --- service/OrderService.java ---
package com.project.shopapp.domains.sales.service;
import com.project.shopapp.domains.sales.dto.request.OrderCheckoutRequest;
import com.project.shopapp.domains.sales.dto.response.OrderResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface OrderService {
    OrderResponse checkout(Integer userId, OrderCheckoutRequest request);
    PageResponse<OrderResponse> getMyOrders(Integer userId, int page, int size);
    void cancelOrder(Integer userId, Long orderId);
}