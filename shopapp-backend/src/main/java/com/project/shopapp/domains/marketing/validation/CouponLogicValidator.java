package com.project.shopapp.domains.marketing.validation;
import com.project.shopapp.domains.marketing.dto.request.CouponCreateRequest;
import com.project.shopapp.domains.marketing.enums.CouponDiscountType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class CouponLogicValidator implements ConstraintValidator<ValidCouponLogic, CouponCreateRequest> {
    @Override
    public boolean isValid(CouponCreateRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        // 1. Kiểm tra ngày tháng
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getEndDate().isBefore(request.getStartDate())) {
                context.buildConstraintViolationWithTemplate("Ngày kết thúc phải lớn hơn ngày bắt đầu")
                        .addPropertyNode("endDate").addConstraintViolation();
                isValid = false;
            }
        }

        // 2. Kiểm tra logic giá trị
        if (CouponDiscountType.PERCENTAGE.name().equals(request.getDiscountType())) {
            if (request.getDiscountValue() != null && request.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {
                context.buildConstraintViolationWithTemplate("Giảm giá theo phần trăm không được vượt quá 100%")
                        .addPropertyNode("discountValue").addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}