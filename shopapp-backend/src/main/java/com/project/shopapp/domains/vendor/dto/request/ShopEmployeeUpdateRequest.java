package com.project.shopapp.domains.vendor.dto.request;
import com.project.shopapp.domains.vendor.validation.ValidShopEmployeeRole;
import lombok.Data;

@Data
public class ShopEmployeeUpdateRequest {
    @ValidShopEmployeeRole
    private String role;
    private String status; // ACTIVE hoặc RESIGNED
}