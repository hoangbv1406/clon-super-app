package com.project.shopapp.domains.inventory.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class TransactionDetailResponse {
    private Long id;
    private Long transactionId;
    private Integer productId;
    private Integer variantId;
    private Integer productItemId;
    private String productName;  // Tên sản phẩm để dễ đọc
    private String variantName;  // Tên biến thể (nếu có)
    private Integer quantityChanged;
    private Integer stockBefore;
    private Integer stockAfter;
    private BigDecimal unitCost;
    private BigDecimal lineTotal; // quantity_changed * unitCost
}