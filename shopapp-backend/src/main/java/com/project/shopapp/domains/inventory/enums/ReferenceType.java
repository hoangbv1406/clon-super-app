package com.project.shopapp.domains.inventory.enums;
public enum ReferenceType {
    ORDER,          // Link với bảng orders (Xuất bán)
    PURCHASE_ORDER, // Link với phiếu nhập từ Supplier (Nhập hàng)
    MANUAL          // Admin tạo tay
}