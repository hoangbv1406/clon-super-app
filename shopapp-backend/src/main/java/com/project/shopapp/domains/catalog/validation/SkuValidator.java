package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SkuValidator implements ConstraintValidator<ValidSku, String> {
    private static final Pattern SKU_PATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Cho phép rỗng để hệ thống tự sinh
        return SKU_PATTERN.matcher(value).matches();
    }
}