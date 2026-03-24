package com.project.shopapp.domains.identity.dto.request;
import com.project.shopapp.domains.identity.validation.ValidDeviceLabel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CredentialRegisterRequest {
    @NotBlank(message = "Credential ID không được để trống")
    private String credentialId;

    @NotBlank(message = "Public Key không được để trống")
    private String publicKey;

    @ValidDeviceLabel
    private String deviceLabel; // Ví dụ: "iPhone 15 Pro Max của Tùng"

    private String authenticatorType;
}