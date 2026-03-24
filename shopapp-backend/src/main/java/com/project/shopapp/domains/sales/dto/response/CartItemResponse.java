// --- response/CartItemResponse.java ---
package com.project.shopapp.domains.sales.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CartItemResponse {
    private Integer id;
    private Integer productId;
    private Integer variantId;
    private Integer quantity;
    private Boolean isSelected;

    // --- Hydrated Data từ Module Catalog ---
    private String productName;
    private String variantName;
    private String imageUrl;
    private BigDecimal currentPrice; // Giá MỚI NHẤT từ DB
    private BigDecimal priceAtAdd;   // Giá lúc bỏ vào giỏ

    // --- Warning Flags (UX xịn xò) ---
    private Boolean isPriceChanged;  // FE hiển thị: "Giá đã thay đổi"
    private Boolean isOutOfStock;    // FE làm mờ (disable) item này đi
    private Integer currentStock;
    private BigDecimal lineTotal;    // currentPrice * quantity
}