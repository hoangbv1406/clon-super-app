package com.project.shopapp.domains.vendor.api.impl;

import com.project.shopapp.domains.vendor.api.ShopEmployeeInternalApi;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.domains.vendor.enums.EmployeeStatus;
import com.project.shopapp.domains.vendor.repository.ShopEmployeeRepository;
import com.project.shopapp.domains.vendor.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ShopEmployeeInternalApiImpl implements ShopEmployeeInternalApi {

    private final ShopEmployeeRepository empRepo;
    private final ShopRepository shopRepo;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "shop_permissions", key = "#shopId + '_' + #userId") // Cache quyền hạn
    public boolean hasPermissionInShop(Integer userId, Integer shopId, String... allowedRoles) {
        // 1. Kiểm tra xem có phải Chủ Shop gốc không (Chủ shop có full quyền)
        Shop shop = shopRepo.findById(shopId).orElse(null);
        if (shop != null && shop.getOwnerId().equals(userId)) {
            return true;
        }

        // 2. Nếu không phải chủ, kiểm tra bảng nhân viên
        return empRepo.findByShopIdAndUserIdAndIsDeleted(shopId, userId, 0L)
                .filter(emp -> emp.getStatus() == EmployeeStatus.ACTIVE)
                .map(emp -> Arrays.asList(allowedRoles).contains(emp.getRole().name()))
                .orElse(false);
    }
}