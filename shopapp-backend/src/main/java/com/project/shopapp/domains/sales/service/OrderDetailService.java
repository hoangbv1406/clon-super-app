// --- service/OrderDetailService.java ---
package com.project.shopapp.domains.sales.service;
import com.project.shopapp.domains.sales.dto.response.OrderDetailResponse;
import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponse> getDetailsByOrderId(Long orderId);
    List<OrderDetailResponse> getDetailsByOrderShopId(Integer orderShopId);
    void requestPartialReturn(Integer userId, Integer detailId);
}