// --- validation/ValidFlashSaleTime.java ---
package com.project.shopapp.domains.marketing.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FlashSaleTimeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFlashSaleTime {
    String message() default "Thời gian Flash Sale không hợp lệ (Phải diễn ra ở tương lai và Bắt đầu < Kết thúc)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}