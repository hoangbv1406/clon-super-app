package com.project.shopapp.domains.identity.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class RoleResponse extends BaseResponse {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
}