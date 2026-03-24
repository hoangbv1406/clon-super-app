package com.project.shopapp.domains.pos.validation;
import com.project.shopapp.domains.pos.enums.PosSessionStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PosStatusValidator implements ConstraintValidator<ValidPosStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Cho phép rỗng khi dùng filter
        return Arrays.stream(PosSessionStatus.values()).anyMatch(e -> e.name().equals(value.toUpperCase()));
    }
}