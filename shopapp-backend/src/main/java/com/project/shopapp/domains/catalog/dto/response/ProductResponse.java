package com.project.shopapp.domains.catalog.dto.response;
import com.project.shopapp.domains.catalog.dto.nested.BrandBasicDto;
import com.project.shopapp.domains.catalog.dto.nested.CategoryBasicDto;
import com.project.shopapp.domains.vendor.dto.nested.ShopBasicDto;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ProductResponse extends BaseResponse {
    private Integer id;
    private String name;
    private String slug;
    private BigDecimal price;
    private String thumbnail;
    private String description;
    private Integer quantity; // FE có thể tự trừ đi reserved_quantity để show cho khách
    private Integer availableQuantity; // Tính toán trả về
    private Integer totalSold;
    private Float ratingAvg;
    private Map<String, Object> specs;

    // Embedded Objects để FE hiển thị ngay không cần gọi 3 API khác
    private ShopBasicDto shop;
    private CategoryBasicDto category;
    private BrandBasicDto brand;
}