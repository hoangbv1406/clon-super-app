package com.project.shopapp.domains.catalog.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER}) // Có thể dùng trên field của DTO hoặc trực tiếp trên Parameter của Controller
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOptionType {

    // Câu báo lỗi mặc định trả về FE nếu validate thất bại
    String message() default "Loại hiển thị tùy chọn không hợp lệ (Hệ thống chỉ chấp nhận: TEXT, COLOR_HEX, IMAGE)";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}