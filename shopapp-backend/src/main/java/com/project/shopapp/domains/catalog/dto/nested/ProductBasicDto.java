package com.project.shopapp.domains.catalog.dto.nested;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductBasicDto {
    private Integer id; // Đây là Product ID
    private Integer variantId;
    private String name;
    private String slug; // <--- FIELD BỊ THIẾU GÂY LỖI SỐ 2
    private BigDecimal price;
    private String thumbnail;
    private Integer stockQuantity;

    // Các field phụ dùng cho Cart và Order
    private String variantName;
    private BigDecimal costPrice;
    private Integer warrantyPeriodMonths;
}