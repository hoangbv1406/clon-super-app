// --- service/CartService.java ---
package com.project.shopapp.domains.sales.service;
import com.project.shopapp.domains.sales.dto.response.CartResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface CartService {
    CartResponse getMyCart(Integer userId, String sessionId);
    void mergeGuestCartToUser(Integer userId, String sessionId);
    PageResponse<CartResponse> getCartsForAdmin(String status, Boolean isGuest, int page, int size);
}