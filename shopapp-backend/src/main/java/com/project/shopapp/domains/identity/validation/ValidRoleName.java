package com.project.shopapp.domains.identity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoleName {
    String message() default "Tên Role chỉ được chứa chữ cái viết thường và dấu gạch dưới (VD: customer_support)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
// Note: RoleNameValidator sẽ dùng regex: "^[a-z_]+$"