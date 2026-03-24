package com.project.shopapp.domains.vendor.dto.request;
import com.project.shopapp.domains.vendor.validation.ValidShopEmployeeRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopEmployeeCreateRequest {
    @NotNull(message = "ID của user không được để trống")
    private Integer userId; // Nhân viên phải có tài khoản trên sàn trước

    @ValidShopEmployeeRole
    private String role;
}