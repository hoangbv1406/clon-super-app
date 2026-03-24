// --- request/FollowRequest.java ---
package com.project.shopapp.domains.social.dto.request;
import com.project.shopapp.domains.social.validation.ValidFollowTarget;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ValidFollowTarget // Custom validator check logic 1 trong 2
public class FollowRequest {
    @NotBlank(message = "Loại theo dõi không được để trống (USER/SHOP)")
    private String followType;

    private Integer targetUserId;
    private Integer targetShopId;
}