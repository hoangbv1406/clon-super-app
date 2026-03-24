package com.project.shopapp.domains.vendor.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopEmployeeConfig {
    // TODO: Setup riêng RedisCacheManager cho "shop_permissions"
    // TTL = 15 phút (Để nếu đổi quyền, tối đa 15p sau hệ thống sẽ ép lấy quyền mới).
}