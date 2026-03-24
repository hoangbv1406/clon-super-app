package com.project.shopapp.domains.catalog.validation;
import com.project.shopapp.domains.catalog.enums.CategoryDisplayMode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CategoryDisplayModeValidator implements ConstraintValidator<ValidCategoryDisplayMode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Cho phép null, DB có default
        return Arrays.stream(CategoryDisplayMode.values()).anyMatch(e -> e.name().equals(value.toUpperCase()));
    }
}