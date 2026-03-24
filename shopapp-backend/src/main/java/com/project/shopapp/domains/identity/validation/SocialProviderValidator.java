package com.project.shopapp.domains.identity.validation;

import com.project.shopapp.domains.identity.enums.SocialProvider;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SocialProviderValidator implements ConstraintValidator<ValidSocialProvider, String> {

    // [PRO] Nạp tất cả các giá trị của Enum vào một HashSet ngay khi khởi động.
    // Việc này giúp tốc độ tra cứu (lookup) đạt mức O(1), siêu nhanh so với việc
    // lặp qua mảng Enum.values() ở mỗi request.
    private static final Set<String> ACCEPTED_PROVIDERS = Arrays.stream(SocialProvider.values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // [PRO] Nguyên tắc Đơn nhiệm (Single Responsibility):
        // Nếu giá trị null hoặc rỗng, trả về true.
        // Bắt buộc nhập hay không là việc của @NotBlank ở DTO.
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Chuyển string đầu vào thành chữ in hoa và tra cứu trong Set
        // Điều này giúp API "dễ thở" hơn (Frontend gửi "google" hay "GOOGLE" đều được).
        return ACCEPTED_PROVIDERS.contains(value.toUpperCase());
    }
}