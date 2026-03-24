// --- nested/CartItemBasicDto.java ---
package com.project.shopapp.domains.sales.dto.nested;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemBasicDto {
    private Integer productId;
    private Integer variantId;
    private Integer quantity;
    private BigDecimal currentPrice;
    // Dùng nhúng vào module Checkout để tính tổng tiền cuối cùng
}