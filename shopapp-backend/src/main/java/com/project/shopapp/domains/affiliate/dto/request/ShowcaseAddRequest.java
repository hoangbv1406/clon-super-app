// --- request/ShowcaseAddRequest.java ---
package com.project.shopapp.domains.affiliate.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShowcaseAddRequest {
    @NotNull(message = "Sản phẩm không được để trống")
    private Integer productId;
}