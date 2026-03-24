package com.project.shopapp.domains.marketing.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CouponLogicValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCouponLogic {
    String message() default "Dữ liệu Coupon không hợp lệ (Kiểm tra ngày tháng và giá trị %)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}