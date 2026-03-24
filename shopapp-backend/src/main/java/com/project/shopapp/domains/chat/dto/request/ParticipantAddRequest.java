// --- request/ParticipantAddRequest.java ---
package com.project.shopapp.domains.chat.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParticipantAddRequest {
    @NotNull(message = "ID User không được để trống")
    private Integer userId;
    private String role; // MEMBER hoặc ADMIN
}