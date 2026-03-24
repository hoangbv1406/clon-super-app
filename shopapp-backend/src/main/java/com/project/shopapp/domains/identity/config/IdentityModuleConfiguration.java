package com.project.shopapp.domains.identity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class IdentityModuleConfiguration {
    // TODO: Khởi tạo Beans chung cho module Identity (VD: Bean mã hóa Password BCrypt, Bean JWT Token Provider)
    // Sẽ được implement khi chúng ta code bảng Users
}