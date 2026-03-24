package com.project.shopapp.domains.identity.validation;

import com.project.shopapp.shared.utils.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // [PRO] Nguyên tắc Single Responsibility (Đơn nhiệm) trong Validation:
        // Validator này CHỈ lo việc check định dạng độ mạnh của mật khẩu.
        // Việc check null hay rỗng sẽ được nhường lại cho annotation @NotBlank ở file DTO.
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Tái sử dụng logic Regex đã compile sẵn ở ValidationUtils để tối ưu hiệu năng
        return ValidationUtils.isValidPassword(value);
    }
}