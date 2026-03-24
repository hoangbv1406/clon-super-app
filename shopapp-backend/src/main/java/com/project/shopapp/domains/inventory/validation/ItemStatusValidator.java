package com.project.shopapp.domains.inventory.validation;
import com.project.shopapp.domains.inventory.enums.ItemStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ItemStatusValidator implements ConstraintValidator<ValidItemStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return Arrays.stream(ItemStatus.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}