package com.project.shopapp.domains.vendor.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopRegistrationRequest {
    @NotBlank(message = "Tên shop không được để trống")
    private String name;
    private String description;
    // Note: Khi tạo, status mặc định là PENDING, không cho user tự truyền
}