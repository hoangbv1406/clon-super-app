// --- request/FavoriteRequest.java ---
package com.project.shopapp.domains.catalog.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequest {
    @NotNull(message = "ID Sản phẩm không được để trống")
    private Integer productId;
}