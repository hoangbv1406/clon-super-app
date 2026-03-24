package com.project.shopapp.domains.vendor.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ShopEmployeeRoleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidShopEmployeeRole {
    String message() default "Quyền nhân viên không hợp lệ (MANAGER, SALES, WAREHOUSE)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}