// --- request/FlashSaleItemCreateRequest.java ---
package com.project.shopapp.domains.marketing.dto.request;
import com.project.shopapp.domains.marketing.validation.ValidSaleLimit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
@ValidSaleLimit // Validator chống set giá sale bị âm
public class FlashSaleItemCreateRequest {
    @NotNull(message = "ID Sản phẩm không được để trống")
    private Integer productId;

    private Integer variantId; // Có thể null

    @NotNull(message = "Giá Sale không được để trống")
    @Min(0)
    private BigDecimal promotionalPrice;

    @NotNull(message = "Số lượng giới hạn bán không được để trống")
    @Min(1)
    private Integer quantityLimit;
}