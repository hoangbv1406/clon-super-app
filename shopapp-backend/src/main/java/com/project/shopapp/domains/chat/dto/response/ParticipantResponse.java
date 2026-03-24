// --- response/ParticipantResponse.java ---
package com.project.shopapp.domains.chat.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ParticipantResponse extends BaseResponse {
    private Integer id;
    private Integer roomId;
    private Integer userId;
    private String userName; // Join từ bảng User
    private String avatarUrl; // Join từ bảng User
    private String role;
    private Boolean isMuted;
    private Long lastReadMessageId;
    private LocalDateTime joinedAt;
}