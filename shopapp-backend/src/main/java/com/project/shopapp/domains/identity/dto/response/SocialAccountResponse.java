package com.project.shopapp.domains.identity.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class SocialAccountResponse extends BaseResponse {
    private Integer id;
    private String provider;
    private String email;
    private String name;
    private String avatarUrl;
    // BẢO MẬT: Tuyệt đối KHÔNG trả về providerId để tránh lộ ID nội bộ của mạng xã hội
}