package com.project.shopapp.domains.social.enums;

public enum InteractionType {
    VIEW,       // Lướt qua bài post / sản phẩm (Auto trigger khi cuộn màn hình)
    CLICK,      // Bấm vào xem chi tiết
    LIKE,       // Thả tim
    UNLIKE,     // Bỏ thả tim
    SHARE,      // Bấm nút chia sẻ
    ADD_CART    // Bấm thêm vào giỏ từ bài viết
}