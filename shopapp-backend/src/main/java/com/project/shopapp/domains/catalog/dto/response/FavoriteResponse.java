// --- response/FavoriteResponse.java ---
package com.project.shopapp.domains.catalog.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class FavoriteResponse extends BaseResponse {
    private Integer id;
    private Integer userId;

    // --- Dữ liệu Hydration đắp vào từ bảng Product ---
    private Integer productId;
    private String productName;
    private String productThumbnail;
    private String productPrice;
    private String productSlug;
    private Boolean isOutOfStock; // Báo cho khách biết hàng yêu thích đã hết

    private LocalDateTime createdAt;
}