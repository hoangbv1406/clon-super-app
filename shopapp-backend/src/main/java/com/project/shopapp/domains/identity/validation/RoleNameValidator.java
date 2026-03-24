package com.project.shopapp.domains.identity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class RoleNameValidator implements ConstraintValidator<ValidRoleName, String> {

    // [PRO] Biến tĩnh (static final) để compile regex 1 lần duy nhất lúc khởi động app.
    // Nếu nhét lệnh compile này vào trong hàm isValid, mỗi lần Admin tạo Role mới
    // Java lại phải tốn CPU để phân tích chuỗi regex này.
    private static final String ROLE_NAME_REGEX = "^[a-z_]+$";
    private static final Pattern PATTERN = Pattern.compile(ROLE_NAME_REGEX);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // [PRO] Nguyên tắc Single Responsibility (Đơn nhiệm):
        // Nếu giá trị null hoặc rỗng, ta trả về true.
        // Lý do: Việc ép buộc Admin "phải nhập" là nhiệm vụ của annotation @NotBlank (nằm ở DTO).
        // Validator này CHỈ làm đúng 1 nhiệm vụ: Nếu có nhập, thì phải nhập đúng định dạng.
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Khớp chuỗi đầu vào với Pattern đã biên dịch
        return PATTERN.matcher(value).matches();
    }
}