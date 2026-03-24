// --- request/AffiliateLinkCreateRequest.java ---
package com.project.shopapp.domains.affiliate.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AffiliateLinkCreateRequest {
    @NotNull(message = "ID Sản phẩm không được để trống")
    private Integer productId;

    // Admin có quyền set rate riêng cho KOL
    private Double customCommissionRate;
}