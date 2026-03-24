package com.project.shopapp.domains.review.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ShopReviewResponse extends BaseResponse {
    private Long id;
    private Integer shopId;
    private Integer userId;
    private String reviewerName; // Tên khách hàng (Map từ User)
    private String reviewerAvatar;
    private Integer orderShopId;
    private Byte rating;
    private String content;
    private List<String> images;
    private ShopReviewResponse sellerReply; // Đệ quy 1 tầng để lấy câu trả lời của Shop
}