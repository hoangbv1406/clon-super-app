package com.project.shopapp.domains.catalog.dto.request;
import com.project.shopapp.domains.catalog.validation.ValidOptionCode;
import com.project.shopapp.domains.catalog.validation.ValidOptionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OptionCreateRequest {
    @NotBlank(message = "Mã tùy chọn không được để trống")
    @ValidOptionCode
    private String code;

    @NotBlank(message = "Tên hiển thị không được để trống")
    private String name;

    @ValidOptionType
    private String type;
}