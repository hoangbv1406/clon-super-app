package com.project.shopapp.domains.identity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DeviceLabelValidator implements ConstraintValidator<ValidDeviceLabel, String> {

    // [PRO] Compile regex 1 lần duy nhất ở mức Class (static final) để tối ưu CPU và Memory.
    // Nếu khởi tạo regex bên trong hàm isValid, mỗi lần có request tới nó lại compile lại -> Cực kỳ ngốn tài nguyên.
    private static final String DEVICE_LABEL_REGEX = "^[a-zA-Z0-9\\s_.-]{3,50}$";
    private static final Pattern PATTERN = Pattern.compile(DEVICE_LABEL_REGEX);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // [PRO] Theo chuẩn Jakarta Validation, các Custom Validator thường trả về true nếu value là null.
        // Việc check null/rỗng sẽ được nhường lại cho annotation @NotBlank ở DTO.
        // Cách này giúp annotation @ValidDeviceLabel có thể dùng chung cho cả các field (không bắt buộc nhập).
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Match regex với chuỗi đầu vào
        return PATTERN.matcher(value).matches();
    }
}