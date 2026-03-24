package com.project.shopapp.domains.identity.validation;

import com.project.shopapp.domains.identity.enums.AddressType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class AddressTypeValidator implements ConstraintValidator<ValidAddressType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Cho phép null (nếu FE không gửi lên thì sẽ dùng default HOME ở Entity)
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Kiểm tra xem string FE truyền lên có khớp với các giá trị của Enum AddressType không
        return Arrays.stream(AddressType.values())
                .anyMatch(enumValue -> enumValue.name().equals(value.toUpperCase()));
    }
}