package com.project.shopapp.domains.catalog.validation;
import com.project.shopapp.domains.catalog.enums.BrandTier;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class BrandTierValidator implements ConstraintValidator<ValidBrandTier, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Cho phép rỗng, lấy default của DB
        return Arrays.stream(BrandTier.values()).anyMatch(e -> e.name().equals(value.toUpperCase()));
    }
}