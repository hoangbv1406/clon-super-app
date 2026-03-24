package com.project.shopapp.domains.catalog.dto.nested;
import lombok.Data;

@Data
public class BrandBasicDto {
    private Integer id;
    private String name;
    private String slug;
    private String iconUrl;
    private String tier; // Nhúng vào Product để hiển thị badge "Premium"
}