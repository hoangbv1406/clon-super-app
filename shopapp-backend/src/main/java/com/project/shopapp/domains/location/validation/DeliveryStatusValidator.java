package com.project.shopapp.domains.location.validation;

import com.project.shopapp.domains.location.enums.DeliveryStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * [PRO] Validator kiểm tra xem String từ Request có khớp với Enum DeliveryStatus hay không.
 * Tớ dùng Set để tối ưu hóa tốc độ tìm kiếm (O(1)) thay vì Loop qua List (O(n)).
 */
public class DeliveryStatusValidator implements ConstraintValidator<ValidDeliveryStatus, String> {

    private Set<String> allowedStatuses;

    @Override
    public void initialize(ValidDeliveryStatus constraintAnnotation) {
        // Lấy toàn bộ tên các Enum hợp lệ và lưu vào Set để tra cứu nhanh
        this.allowedStatuses = Arrays.stream(DeliveryStatus.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Nếu field để trống, hãy để @NotNull hoặc @NotBlank lo.
        // Validator này chỉ tập trung vào việc "Nếu có giá trị thì phải đúng format".
        if (value == null || value.isBlank()) {
            return true;
        }

        // Kiểm tra xem giá trị truyền lên có nằm trong danh sách Enum không
        boolean isValid = allowedStatuses.contains(value.toUpperCase());

        if (!isValid) {
            // [TƯ DUY ENTERPRISE] Ghi đè message mặc định để báo cho User biết họ truyền sai cái gì
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Trạng thái '" + value + "' không hợp lệ. Phải là một trong các giá trị: " + allowedStatuses
            ).addConstraintViolation();
        }

        return isValid;
    }
}