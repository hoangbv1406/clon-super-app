package com.project.shopapp.domains.identity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SocialProviderValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSocialProvider {
    String message() default "Nền tảng mạng xã hội không được hỗ trợ (Chỉ chấp nhận GOOGLE, FACEBOOK, APPLE)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
// Note: Tạo SocialProviderValidator để parse String sang Enum SocialProvider