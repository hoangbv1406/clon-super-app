package com.project.shopapp.domains.identity.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class CredentialResponse extends BaseResponse {
    private Integer id;
    private String deviceLabel;
    private String authenticatorType;
    private Boolean isActive;
    private LocalDateTime lastUsedAt;
    // BẢO MẬT: KHÔNG BAO GIỜ TRẢ VỀ credentialId và publicKey ra API GET cho FE
}