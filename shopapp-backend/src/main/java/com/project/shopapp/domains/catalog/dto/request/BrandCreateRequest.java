package com.project.shopapp.domains.catalog.dto.request;
import com.project.shopapp.domains.catalog.validation.ValidBrandTier;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BrandCreateRequest {
    @NotBlank(message = "Tên thương hiệu không được để trống")
    private String name;

    private String iconUrl;
    private String description;

    @ValidBrandTier
    private String tier;
}