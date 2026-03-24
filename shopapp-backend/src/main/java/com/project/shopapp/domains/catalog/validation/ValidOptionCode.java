package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOptionCode {
    String message() default "Mã Code chỉ được chứa chữ cái in hoa và dấu gạch dưới (VD: COLOR, STORAGE_SIZE)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}