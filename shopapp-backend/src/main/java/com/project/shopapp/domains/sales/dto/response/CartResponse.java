// --- response/CartResponse.java ---
package com.project.shopapp.domains.sales.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class CartResponse extends BaseResponse {
    private Integer id;
    private Integer userId;
    private String sessionId;
    private String status;
    private LocalDateTime expiresAt;

    // Các field này sẽ được Service tính toán và fill vào từ bảng cart_items
    private Integer totalItems;
    private BigDecimal totalPrice;
}