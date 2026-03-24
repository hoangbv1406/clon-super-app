// --- request/ParticipantSettingsRequest.java ---
package com.project.shopapp.domains.chat.dto.request;
import lombok.Data;

@Data
public class ParticipantSettingsRequest {
    private Boolean isMuted; // Dùng để bật/tắt chuông phòng chat
}