package com.project.shopapp.domains.location.validation;

import com.project.shopapp.domains.location.enums.DistrictType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * [PRO] Validator kiểm tra tính hợp lệ của Loại hình Quận/Huyện.
 * Đảm bảo dữ liệu đầu vào khớp hoàn toàn với cấu hình logic vận chuyển của hệ thống.
 */
public class DistrictTypeValidator implements ConstraintValidator<ValidDistrictType, String> {

    private Set<String> allowedTypes;

    @Override
    public void initialize(ValidDistrictType constraintAnnotation) {
        // Cache lại danh sách enum dưới dạng Set<String> để check O(1)
        this.allowedTypes = Arrays.stream(DistrictType.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Nếu giá trị null hoặc rỗng, ta coi là hợp lệ (để @NotBlank hoặc @NotNull xử lý riêng nếu cần)
        if (value == null || value.isBlank()) {
            return true;
        }

        // Kiểm tra xem giá trị gửi lên có nằm trong danh sách URBAN, RURAL, ISLAND, UNKNOWN không
        boolean isValid = allowedTypes.contains(value.toUpperCase());

        if (!isValid) {
            // [TƯ DUY ENTERPRISE] Tùy chỉnh thông báo lỗi để Dev FE hoặc Integrator biết đường mà sửa
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Loại hình '" + value + "' không được hỗ trợ. Vui lòng sử dụng một trong các loại: " + allowedTypes
            ).addConstraintViolation();
        }

        return isValid;
    }
}