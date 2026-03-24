package com.project.shopapp.domains.vendor.dto.request;
import com.project.shopapp.domains.vendor.validation.ValidShopStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopStatusUpdateRequest {
    @ValidShopStatus
    private String status;
    @NotBlank(message = "Lý do cập nhật trạng thái (để ghi log)")
    private String reason;
}