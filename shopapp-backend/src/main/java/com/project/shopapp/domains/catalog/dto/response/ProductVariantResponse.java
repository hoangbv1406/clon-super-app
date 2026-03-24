package com.project.shopapp.domains.catalog.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ProductVariantResponse extends BaseResponse {
    private Integer id;
    private Integer productId;
    private String sku;
    private String name; // Trả về Tên tự sinh từ DB (VD: Đỏ - 256GB)
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String imageUrl;
    private Integer availableStock; // Đã trừ reserved
    private BigDecimal weight;
    private String dimensions;
    private Map<String, String> attributes;
    private Boolean isActive;
}