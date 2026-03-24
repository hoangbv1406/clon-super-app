package com.project.shopapp.domains.identity.dto.request;
import com.project.shopapp.domains.identity.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "Tên không được để trống")
    private String fullName;
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
    @ValidPassword // Tái sử dụng ValidPassword custom
    private String password;
    private String phoneNumber;
}