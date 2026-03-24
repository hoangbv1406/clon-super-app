package com.project.shopapp.domains.identity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TokenFormatValidator implements ConstraintValidator<ValidTokenFormat, String> {

    // [PRO] Token của chúng ta có dạng UUID (36 ký tự) hoặc ID.UUID (VD: 1234.550e8400-e29b-41d4-a716-446655440000)
    // Giới hạn độ dài từ 16 đến 255 ký tự. Chỉ cho phép chữ, số, dấu gạch ngang, gạch dưới và dấu chấm.
    // Việc này chặn đứng hoàn toàn các payload chứa mã độc (SQLi, XSS) hoặc payload rác siêu dài.
    private static final String TOKEN_REGEX = "^[a-zA-Z0-9\\-_\\.]{16,255}$";
    private static final Pattern PATTERN = Pattern.compile(TOKEN_REGEX);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // [PRO] Nguyên tắc Single Responsibility (Đơn nhiệm):
        // Nếu giá trị null hoặc rỗng, trả về true.
        // Bắt buộc nhập hay không là việc của @NotBlank ở DTO RefreshTokenRequest.
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // [PRO] Trick chống ReDoS: Check độ dài thô trước khi ném vào Regex Engine.
        // Nếu hacker cố tình gửi chuỗi 1 triệu ký tự, lệnh length() sẽ chặn đứng nó trong O(1)
        // thay vì để Regex phải quét qua 1 triệu ký tự gây ngẽn CPU.
        if (value.length() > 255) {
            return false;
        }

        return PATTERN.matcher(value).matches();
    }
}