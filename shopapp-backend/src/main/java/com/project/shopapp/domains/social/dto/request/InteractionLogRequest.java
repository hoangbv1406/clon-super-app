// --- request/InteractionLogRequest.java ---
package com.project.shopapp.domains.social.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InteractionLogRequest {
    private Integer productId;
    private Long postId;

    @NotBlank(message = "Loại hành động không được trống")
    private String actionType;

    private Integer durationMs;
    private String deviceId;
}