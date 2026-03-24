// --- request/ReviewReplyRequest.java ---
package com.project.shopapp.domains.community.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewReplyRequest {
    @NotBlank(message = "Nội dung phản hồi không được để trống")
    private String content;
}