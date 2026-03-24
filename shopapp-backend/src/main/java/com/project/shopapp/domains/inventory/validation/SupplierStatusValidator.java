package com.project.shopapp.domains.inventory.validation;
import com.project.shopapp.domains.inventory.enums.SupplierStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class SupplierStatusValidator implements ConstraintValidator<ValidSupplierStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return Arrays.stream(SupplierStatus.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}