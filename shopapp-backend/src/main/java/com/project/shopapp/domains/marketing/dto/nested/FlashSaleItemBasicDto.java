// --- nested/FlashSaleItemBasicDto.java ---
package com.project.shopapp.domains.marketing.dto.nested;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FlashSaleItemBasicDto {
    private Integer flashSaleId;
    private Long flashSaleItemId;
    private BigDecimal promotionalPrice;
    // Dùng để nhúng thẳng vào API /products/{id} để FE hiển thị Tag "Đang Sale"
}