package com.project.shopapp.domains.marketing.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ApplicableRuleValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidApplicableRule {

    // Câu báo lỗi mặc định trả về cho Frontend
    String message() default "Luật áp dụng không hợp lệ (Hệ thống chỉ chấp nhận: INCLUDE hoặc EXCLUDE)";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}