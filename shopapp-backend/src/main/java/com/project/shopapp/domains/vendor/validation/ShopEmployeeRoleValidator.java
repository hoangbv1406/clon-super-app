package com.project.shopapp.domains.vendor.validation;
import com.project.shopapp.domains.vendor.enums.ShopEmployeeRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ShopEmployeeRoleValidator implements ConstraintValidator<ValidShopEmployeeRole, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return Arrays.stream(ShopEmployeeRole.values()).anyMatch(e -> e.name().equals(value.toUpperCase()));
    }
}