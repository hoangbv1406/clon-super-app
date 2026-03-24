package com.project.shopapp.domains.identity.validation;

import com.project.shopapp.shared.utils.ValidationUtils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Mật khẩu tối thiểu 8 ký tự, gồm chữ cái và số";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}