package com.project.shopapp.domains.location.validation;

import com.project.shopapp.domains.location.enums.Region;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * [PRO] Validator kiểm tra tính hợp lệ của Vùng miền (Vùng địa lý).
 * Đảm bảo dữ liệu đầu vào chuẩn xác để phục vụ logic tính phí vận chuyển của hệ thống.
 */
public class RegionValidator implements ConstraintValidator<ValidRegion, String> {

    private Set<String> allowedRegions;

    @Override
    public void initialize(ValidRegion constraintAnnotation) {
        // [PERFORMANCE] Cache lại danh sách enum dưới dạng Set để đạt độ phức tạp O(1) khi kiểm tra
        this.allowedRegions = Arrays.stream(Region.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Cho phép null hoặc trống (để @NotBlank/@NotNull lo nếu cần bắt buộc)
        if (value == null || value.isBlank()) {
            return true;
        }

        // Kiểm tra tính tồn tại (không phân biệt hoa thường)
        boolean isValid = allowedRegions.contains(value.toUpperCase());

        if (!isValid) {
            // [TƯ DUY ENTERPRISE] Ghi đè thông báo lỗi mặc định bằng một hướng dẫn chi tiết
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Vùng miền '" + value + "' không hợp lệ. Các giá trị được phép: " + allowedRegions
            ).addConstraintViolation();
        }

        return isValid;
    }
}