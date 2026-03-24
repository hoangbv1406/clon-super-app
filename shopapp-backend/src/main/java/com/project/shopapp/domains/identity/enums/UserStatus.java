package com.project.shopapp.domains.identity.enums;

public enum UserStatus {
    ACTIVE,
    INACTIVE, // Chưa verify email
    LOCKED,   // Bị khóa do nhập sai pass nhiều lần
    BANNED    // Bị Admin cấm vĩnh viễn
}