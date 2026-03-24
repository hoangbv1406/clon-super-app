// --- request/CartItemUpdateRequest.java ---
package com.project.shopapp.domains.sales.dto.request;
import com.project.shopapp.domains.sales.validation.ValidCartQuantity;
import lombok.Data;

@Data
public class CartItemUpdateRequest {
    @ValidCartQuantity
    private Integer quantity;
    private Boolean isSelected;
}