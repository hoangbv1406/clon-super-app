package com.project.shopapp.domains.catalog.dto.nested;
import lombok.Data;

@Data
public class ProductImageBasicDto {
    private String imageUrl;
    private Integer displayOrder;
    // Lược giản nhất để nhúng thẳng một List<ProductImageBasicDto> vào bên trong ProductResponse
}