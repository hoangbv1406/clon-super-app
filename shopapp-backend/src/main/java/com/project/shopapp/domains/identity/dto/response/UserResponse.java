package com.project.shopapp.domains.identity.dto.response;
import com.project.shopapp.domains.identity.dto.nested.RoleBasicDto;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class UserResponse extends BaseResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String profileImage;
    private Boolean isActive;
    private RoleBasicDto role; // Nested DTO
}