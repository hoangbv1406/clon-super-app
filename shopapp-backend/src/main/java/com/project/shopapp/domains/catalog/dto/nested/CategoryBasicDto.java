package com.project.shopapp.domains.catalog.dto.nested;
import lombok.Data;

@Data
public class CategoryBasicDto {
    private Integer id;
    private String name;
    private String slug;
    // Phục vụ làm Breadcrumb (Trang chủ > Điện thoại > Apple)
}