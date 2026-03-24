// --- response/FollowResponse.java ---
package com.project.shopapp.domains.social.dto.response;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class FollowResponse {
    private Integer id;
    private Integer followerId;
    private String followerName;
    private String followerAvatar;

    private String followType;
    private Integer targetId;
    private String targetName;
    private String targetAvatar;

    private LocalDateTime createdAt;
}