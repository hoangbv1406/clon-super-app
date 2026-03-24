package com.project.shopapp.domains.catalog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            // Dùng Reflection để lấy giá trị của 2 trường startDate và endDate từ Object truyền vào
            Field startDateField = value.getClass().getDeclaredField("startDate");
            Field endDateField = value.getClass().getDeclaredField("endDate");

            startDateField.setAccessible(true);
            endDateField.setAccessible(true);

            LocalDateTime startDate = (LocalDateTime) startDateField.get(value);
            LocalDateTime endDate = (LocalDateTime) endDateField.get(value);

            // Nếu 1 trong 2 null thì bỏ qua, việc check Null sẽ do @NotNull đảm nhận
            if (startDate == null || endDate == null) {
                return true;
            }

            // Logic chính: Start Date không được phép nằm sau End Date
            return !startDate.isAfter(endDate);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Nếu Class được gắn annotation không có 2 trường này, báo lỗi cấu hình
            throw new IllegalArgumentException("Class gắn @ValidDateRange phải có 2 trường 'startDate' và 'endDate'");
        }
    }
}