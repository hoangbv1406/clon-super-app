package com.project.shopapp.domains.catalog.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OptionValueCreateRequest {
    @NotNull(message = "ID Tùy chọn gốc không được để trống")
    private Integer optionId;

    @NotBlank(message = "Giá trị không được để trống")
    private String value;

    private String metaData; // FE sẽ truyền #FF0000 nếu là màu
    private Integer displayOrder;
}