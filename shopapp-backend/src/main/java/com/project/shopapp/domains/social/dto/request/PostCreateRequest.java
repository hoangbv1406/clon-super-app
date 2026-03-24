// --- request/PostCreateRequest.java ---
package com.project.shopapp.domains.social.dto.request;
import com.project.shopapp.domains.social.validation.ValidMediaPayload;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
@ValidMediaPayload
public class PostCreateRequest {
    private String content;

    @NotBlank(message = "Loại media không được trống (IMAGE/VIDEO)")
    private String mediaType;

    private List<String> mediaUrls;

    private Integer linkedProductId; // Cho phép gắn giỏ hàng
}