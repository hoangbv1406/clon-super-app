package com.project.shopapp.domains.identity.dto.request;
import com.project.shopapp.domains.identity.validation.ValidRoleName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequest {
    @NotBlank(message = "Tên role không được để trống")
    @ValidRoleName
    private String name;
    private String description;
}