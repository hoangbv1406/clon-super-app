// --- validation/FlashSaleTimeValidator.java ---
package com.project.shopapp.domains.marketing.validation;
import com.project.shopapp.domains.marketing.dto.request.FlashSaleCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FlashSaleTimeValidator implements ConstraintValidator<ValidFlashSaleTime, FlashSaleCreateRequest> {
    @Override
    public boolean isValid(FlashSaleCreateRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;
        if (request.getStartTime() == null || request.getEndTime() == null) return true;

        LocalDateTime now = LocalDateTime.now();
        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (request.getStartTime().isBefore(now)) {
            context.buildConstraintViolationWithTemplate("Thời gian bắt đầu phải ở tương lai")
                    .addPropertyNode("startTime").addConstraintViolation();
            isValid = false;
        }

        if (request.getEndTime().isBefore(request.getStartTime())) {
            context.buildConstraintViolationWithTemplate("Thời gian kết thúc phải diễn ra sau thời gian bắt đầu")
                    .addPropertyNode("endTime").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}