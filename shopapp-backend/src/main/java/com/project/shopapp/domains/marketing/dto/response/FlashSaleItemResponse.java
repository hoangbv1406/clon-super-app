// --- response/FlashSaleItemResponse.java ---
package com.project.shopapp.domains.marketing.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class FlashSaleItemResponse extends BaseResponse {
    private Long id;
    private Integer flashSaleId;
    private Integer productId;
    private Integer variantId;
    private String productName;  // Tên SP lấy từ Catalog
    private String productImage; // Ảnh SP lấy từ Catalog
    private BigDecimal promotionalPrice;
    private Integer quantityLimit;
    private Integer soldCount;
    private Boolean isSoldOut;

    // Thuộc tính để Frontend tính % sale (Giảm 50%, Giảm 30%)
    private BigDecimal originalPrice;
}