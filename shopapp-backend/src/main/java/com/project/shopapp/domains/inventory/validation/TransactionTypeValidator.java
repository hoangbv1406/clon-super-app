package com.project.shopapp.domains.inventory.validation;
import com.project.shopapp.domains.inventory.enums.TransactionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class TransactionTypeValidator implements ConstraintValidator<ValidTransactionType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false; // Bắt buộc nhập
        return Arrays.stream(TransactionType.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}