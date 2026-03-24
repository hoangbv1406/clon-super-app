package com.project.shopapp.domains.vendor.api;

public interface ShopEmployeeInternalApi {
    /**
     * Trả về TRUE nếu User là Chủ Shop (Owner) hoặc là Nhân viên có Role chỉ định
     * @param allowedRoles VD: "MANAGER", "WAREHOUSE"
     */
    boolean hasPermissionInShop(Integer userId, Integer shopId, String... allowedRoles);
}