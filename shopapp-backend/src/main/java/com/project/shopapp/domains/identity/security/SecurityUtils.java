package com.project.shopapp.domains.identity.security;

import com.project.shopapp.domains.identity.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    /**
     * @return ID của user đang đăng nhập (Kiểu Integer), hoặc null nếu chưa login
     */
    public Integer getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        // Cú pháp truyền thống dễ hiểu cho mọi version Java
        if (principal instanceof User) {
            User user = (User) principal; // Ép kiểu thủ công
            return user.getId();
        }
        return null;
    }

    /**
     * @return Email của user đang đăng nhập, hoặc null nếu chưa login
     */
    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        // Cú pháp truyền thống
        if (principal instanceof User) {
            User user = (User) principal; // Ép kiểu thủ công
            return user.getEmail();
        }
        return null;
    }

// --- Bổ sung vào file: domains/identity/security/SecurityUtils.java ---

    /**
     * Hàm dùng chung để kiểm tra một Role bất kỳ
     */
    public boolean hasRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // Spring Security thường lưu quyền với prefix "ROLE_" (VD: ROLE_USER, ROLE_VENDOR)
        // Ta chuẩn hóa chuỗi để check cho chắc chắn
        String targetRole = roleName.startsWith("ROLE_") ? roleName.toUpperCase() : "ROLE_" + roleName.toUpperCase();

        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(targetRole) ||
                        authority.getAuthority().equals(roleName.toUpperCase()));
    }

    /**
     * @return true nếu người dùng có Role là USER
     */
    public boolean isUser() {
        return hasRole("USER");
    }

    /**
     * @return true nếu người dùng thuộc nhóm của Shop (VENDOR, MANAGER, CSKH)
     */
    public boolean isVendor() {
        return hasRole("VENDOR") || hasRole("MANAGER") || hasRole("CSKH");
    }

}
