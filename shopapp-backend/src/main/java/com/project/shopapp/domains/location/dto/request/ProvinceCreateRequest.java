package com.project.shopapp.domains.location.dto.request;
import com.project.shopapp.domains.location.validation.ValidRegion;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProvinceCreateRequest {
    @NotBlank(message = "Mã tỉnh không được để trống")
    private String code;
    @NotBlank(message = "Tên tỉnh không được để trống")
    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    @ValidRegion // Custom Validator
    private String region;
}