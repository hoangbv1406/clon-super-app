// --- api/CartItemInternalApi.java ---
package com.project.shopapp.domains.sales.api;
import com.project.shopapp.domains.sales.dto.nested.CartItemBasicDto;
import java.util.List;

public interface CartItemInternalApi {
    // Lấy các item đang được Select để Checkout
    List<CartItemBasicDto> getSelectedItemsForCheckout(Integer cartId);

    // Dọn dẹp các item đã thanh toán xong
    void clearSelectedItems(Integer cartId);
}