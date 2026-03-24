package com.project.shopapp.domains.inventory.dto.request;
import com.project.shopapp.domains.inventory.validation.ValidQuantityChanged;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionDetailCreateRequest {
    @NotNull(message = "ID Sản phẩm không được để trống")
    private Integer productId;

    private Integer variantId;
    private Integer productItemId;

    @ValidQuantityChanged
    private Integer quantityChanged;

    @Min(value = 0, message = "Giá vốn không được âm")
    private BigDecimal unitCost;
}