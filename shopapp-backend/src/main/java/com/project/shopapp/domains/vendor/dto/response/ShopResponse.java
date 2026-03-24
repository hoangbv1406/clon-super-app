package com.project.shopapp.domains.vendor.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ShopResponse extends BaseResponse {
    private Integer id;
    private Integer ownerId;
    private String name;
    private String slug;
    private String logoUrl;
    private String bannerUrl;
    private String description;
    private String status;
    private Float ratingAvg;
    private Integer totalOrders;
    // BẢO MẬT: commissionRate (Phí sàn) KHÔNG trả về cho end-user, chỉ có Admin/Chủ shop được thấy (sẽ dùng DTO riêng cho Admin).
}