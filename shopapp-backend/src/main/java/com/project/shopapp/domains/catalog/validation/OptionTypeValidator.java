package com.project.shopapp.domains.catalog.validation;

import com.project.shopapp.domains.catalog.enums.OptionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class OptionTypeValidator implements ConstraintValidator<ValidOptionType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // [PRO TIP]: Cho phép null hoặc chuỗi rỗng đi qua.
        // Bởi vì ở Entity Option, chúng ta đã set @Builder.Default private OptionType type = OptionType.TEXT;
        // Nếu bắt buộc nhập, ta sẽ kết hợp thêm @NotBlank ở DTO chứ không gộp vào Validator này để tái sử dụng.
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Chuyển string FE truyền lên thành chữ hoa (để tránh lỗi text/TEXT)
        // và so khớp với các constant của Enum OptionType
        return Arrays.stream(OptionType.values())
                .anyMatch(enumValue -> enumValue.name().equals(value.toUpperCase().trim()));
    }
}