package com.project.shopapp.domains.catalog.dto.nested;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductVariantBasicDto {
    private Integer id;
    private String sku;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    // Gắn vào CartItem hoặc OrderDetail
}