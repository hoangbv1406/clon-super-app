package com.project.shopapp.domains.catalog.dto.nested;
import lombok.Data;

@Data
public class OptionValueBasicDto {
    private Integer id;
    private String value;
    private String metaData;
    // Dùng để map nhanh vào JSON của ProductVariant
}