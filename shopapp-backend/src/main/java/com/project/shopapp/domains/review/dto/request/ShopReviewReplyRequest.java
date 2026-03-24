package com.project.shopapp.domains.review.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopReviewReplyRequest {
    @NotBlank(message = "Nội dung phản hồi không được để trống")
    private String content;
}