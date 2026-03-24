package com.project.shopapp.domains.catalog.dto.request;
import com.project.shopapp.domains.catalog.validation.ValidProductType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductCreateRequest {
    @NotBlank(message = "Tên sản phẩm không được trống")
    private String name;

    @NotNull(message = "Shop ID không được trống")
    private Integer shopId;
    private Integer categoryId;
    private Integer brandId;

    @Min(value = 0, message = "Giá không được âm")
    private BigDecimal price;

    private String thumbnail;
    private String description;

    @ValidProductType
    private String productType;

    private Integer warrantyPeriod;
    private Boolean isImeiTracked;
    private Map<String, Object> specs;
}