// --- response/ReviewResponse.java ---
package com.project.shopapp.domains.community.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ReviewResponse extends BaseResponse {
    private Integer id;
    private Integer productId;
    private Integer userId;
    private String userName; // Tên khách hàng (hoặc ẩn danh "T***g")
    private String userAvatar;
    private String variantName; // Tên phân loại hàng (Lấy từ orderDetail)
    private Integer rating;
    private String content;
    private List<String> images;
    private Integer helpfulCount;
    private LocalDateTime createdAt;

    // Nếu có Shop vào Reply, nó sẽ nằm ở đây
    private ReviewResponse shopReply;
}