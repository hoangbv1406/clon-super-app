package com.project.shopapp.domains.location.dto.request;
import com.project.shopapp.domains.location.validation.ValidDistrictType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DistrictCreateRequest {
    @NotBlank(message = "Mã huyện không được để trống")
    private String code;
    @NotBlank(message = "Mã tỉnh không được để trống")
    private String provinceCode;
    @NotBlank(message = "Tên huyện không được để trống")
    private String name;
    private String fullName;
    @ValidDistrictType
    private String type;
}