package com.project.shopapp.domains.vendor.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Cấu hình riêng cho Module Vendor (Nhà bán).
 * Bao gồm quản lý thông tin Shop, Nhân viên Shop (Shop Employees) và Cấu hình vận chuyển của Shop.
 */
@Configuration
@ComponentScan(basePackages = "com.project.shopapp.domains.vendor")
public class VendorModuleConfiguration {
    // TODO: Khai báo các Bean đặc thù của Module Vendor (Ví dụ Vendor Analytics Client)
}