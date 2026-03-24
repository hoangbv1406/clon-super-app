// --- service/CartItemService.java ---
package com.project.shopapp.domains.sales.service;
import com.project.shopapp.domains.sales.dto.request.CartItemAddRequest;
import com.project.shopapp.domains.sales.dto.request.CartItemUpdateRequest;
import com.project.shopapp.domains.sales.dto.response.CartItemResponse;
import com.project.shopapp.domains.sales.dto.response.CartResponse; // Từ bài trước
import java.util.List;

public interface CartItemService {
    // Hàm này sẽ fill data cho CartResponse của bài trước
    CartResponse getFullCart(Integer userId, String sessionId);

    void addItem(Integer userId, String sessionId, CartItemAddRequest request);
    void updateItem(Integer userId, String sessionId, Integer itemId, CartItemUpdateRequest request);
    void removeItem(Integer userId, String sessionId, Integer itemId);
    void toggleSelectAll(Integer userId, String sessionId, boolean isSelected);
}