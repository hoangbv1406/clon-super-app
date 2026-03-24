// --- validation/SessionIdValidator.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SessionIdValidator implements ConstraintValidator<ValidSessionId, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return false;
        return value.length() >= 16;
    }
}