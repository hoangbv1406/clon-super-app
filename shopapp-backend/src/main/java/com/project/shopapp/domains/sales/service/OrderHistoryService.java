// --- service/OrderHistoryService.java ---
package com.project.shopapp.domains.sales.service;
import com.project.shopapp.domains.sales.dto.response.OrderHistoryResponse;
import java.util.List;

public interface OrderHistoryService {
    List<OrderHistoryResponse> getOrderTimeline(Long orderId);
    List<OrderHistoryResponse> getOrderShopTimeline(Integer orderShopId);
}