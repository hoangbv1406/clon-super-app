package com.project.shopapp.domains.identity.api;

public interface RoleInternalApi {
    /**
     * Dùng cho nội bộ Backend: Lấy Role ID dựa trên Tên Role một cách nhanh chóng (có Cache)
     */
    Integer getDefaultRoleId(String roleName);
}