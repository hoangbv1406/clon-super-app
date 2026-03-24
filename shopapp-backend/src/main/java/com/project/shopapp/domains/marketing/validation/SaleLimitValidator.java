package com.project.shopapp.domains.marketing.validation;
import com.project.shopapp.domains.marketing.dto.request.FlashSaleItemCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class SaleLimitValidator implements ConstraintValidator<ValidSaleLimit, FlashSaleItemCreateRequest> {
    @Override
    public boolean isValid(FlashSaleItemCreateRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        if (request.getPromotionalPrice() != null && request.getPromotionalPrice().compareTo(BigDecimal.ZERO) < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Giá sale không được âm")
                    .addPropertyNode("promotionalPrice").addConstraintViolation();
            return false;
        }
        return true;
    }
}