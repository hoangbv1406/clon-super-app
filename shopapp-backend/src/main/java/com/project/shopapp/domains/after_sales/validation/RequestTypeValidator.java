// --- validation/RequestTypeValidator.java ---
package com.project.shopapp.domains.after_sales.validation;
import com.project.shopapp.domains.after_sales.enums.RequestType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RequestTypeValidator implements ConstraintValidator<ValidRequestType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return Arrays.stream(RequestType.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}