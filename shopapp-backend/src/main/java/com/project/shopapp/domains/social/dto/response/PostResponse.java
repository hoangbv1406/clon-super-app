// --- response/PostResponse.java ---
package com.project.shopapp.domains.social.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class PostResponse extends BaseResponse {
    private Long id;
    private Integer userId;
    private String authorName;   // Tên KOC/User
    private String authorAvatar; // Avatar
    private String content;
    private String mediaType;
    private List<String> mediaUrls;

    // UI hiển thị cái card nhỏ nhỏ chứa tên SP và giá
    private Integer linkedProductId;
    private String linkedProductName;
    private String linkedProductPrice;
    private String linkedProductThumbnail;

    private Integer totalLikes;
    private Integer totalComments;
    private Integer totalShares;
    private LocalDateTime createdAt;

    // UI hiển thị: User hiện tại đã like bài này chưa (Computed field)
    private Boolean isLikedByCurrentUser;
}