// --- request/ReviewCreateRequest.java ---
package com.project.shopapp.domains.community.dto.request;
import com.project.shopapp.domains.community.validation.ValidReviewRating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class ReviewCreateRequest {
    @NotNull(message = "Chi tiết đơn hàng không được để trống")
    private Integer orderDetailId;

    @NotNull(message = "ID Sản phẩm không được để trống")
    private Integer productId;

    @ValidReviewRating
    private Integer rating;

    @NotBlank(message = "Vui lòng nhập nội dung đánh giá")
    private String content;

    private List<String> images;
}