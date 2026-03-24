package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class OptionCodeValidator implements ConstraintValidator<ValidOptionCode, String> {
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z_]+$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return CODE_PATTERN.matcher(value).matches();
    }
}