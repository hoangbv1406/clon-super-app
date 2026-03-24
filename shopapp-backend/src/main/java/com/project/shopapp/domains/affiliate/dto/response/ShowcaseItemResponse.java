// --- response/ShowcaseItemResponse.java ---
package com.project.shopapp.domains.affiliate.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ShowcaseItemResponse extends BaseResponse {
    private Integer id;
    private Integer userId;

    // Hydration Data (Dữ liệu đắp vào từ bảng Product)
    private Integer productId;
    private String productName;
    private String productThumbnail;
    private String productPrice;

    // Hydration Data (Dữ liệu đắp vào từ bảng Affiliate Link)
    private String affiliateShortUrl;

    private Boolean isHidden;
    private Integer displayOrder;
}