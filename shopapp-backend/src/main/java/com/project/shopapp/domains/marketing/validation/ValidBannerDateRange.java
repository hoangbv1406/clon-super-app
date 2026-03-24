package com.project.shopapp.domains.marketing.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BannerDateRangeValidator.class)
@Target({ElementType.TYPE}) // Validate chéo 2 field trong 1 class
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBannerDateRange {
    String message() default "Ngày kết thúc phải diễn ra sau ngày bắt đầu";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}