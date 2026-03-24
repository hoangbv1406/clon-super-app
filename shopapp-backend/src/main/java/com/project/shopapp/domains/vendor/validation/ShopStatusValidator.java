package com.project.shopapp.domains.vendor.validation;
import com.project.shopapp.domains.vendor.enums.ShopStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ShopStatusValidator implements ConstraintValidator<ValidShopStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return Arrays.stream(ShopStatus.values()).anyMatch(e -> e.name().equals(value.toUpperCase()));
    }
}