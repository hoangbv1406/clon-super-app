// --- request/CartItemAddRequest.java ---
package com.project.shopapp.domains.sales.dto.request;
import com.project.shopapp.domains.sales.validation.ValidCartQuantity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemAddRequest {
    @NotNull(message = "ID Sản phẩm không được để trống")
    private Integer productId;

    private Integer variantId;

    @ValidCartQuantity
    private Integer quantity;
}