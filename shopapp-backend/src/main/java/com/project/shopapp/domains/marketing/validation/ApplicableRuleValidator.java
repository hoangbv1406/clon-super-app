package com.project.shopapp.domains.marketing.validation;

import com.project.shopapp.domains.marketing.enums.ApplicableRuleType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ApplicableRuleValidator implements ConstraintValidator<ValidApplicableRule, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // [PRO TIP]: Cho phép null hoặc chuỗi rỗng đi qua (return true).
        // Lý do: Ở Entity CouponApplicable, chúng ta đã set Default là INCLUDE
        // (@Builder.Default private ApplicableRuleType applicableType = ApplicableRuleType.INCLUDE;).
        // Nếu FE không gửi lên, hệ thống tự hiểu là thêm luật INCLUDE.
        // Nếu muốn ép buộc FE PHẢI truyền lên, ta sẽ kết hợp thêm @NotBlank ở DTO.
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Chuyển string thành chữ hoa, cắt khoảng trắng 2 đầu và so khớp với Enum
        return Arrays.stream(ApplicableRuleType.values())
                .anyMatch(enumValue -> enumValue.name().equals(value.toUpperCase().trim()));
    }
}