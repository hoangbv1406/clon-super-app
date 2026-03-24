package com.project.shopapp.domains.identity.validation;

import com.project.shopapp.domains.identity.enums.DeviceType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class DeviceTypeValidator implements ConstraintValidator<ValidDeviceType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        // Kiểm tra xem string FE truyền lên có khớp với các hằng số trong Enum không
        return Arrays.stream(DeviceType.values())
                .anyMatch(enumValue -> enumValue.name().equals(value.toUpperCase()));
    }
}