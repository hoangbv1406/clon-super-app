package com.project.shopapp.domains.catalog.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FavoriteMarketingJob {
    // TODO: Viết EventListener bắt sự kiện `PriceDropEvent` (Phát ra khi Admin sửa bảng products để giảm giá).
    // Khi 1 sản phẩm giảm giá, Job này sẽ gọi `FavoriteInternalApi.getUsersWhoFavoritedProduct(productId)`
    // để lấy ra tập hợp Khách hàng (có thể là hàng ngàn người).
    // Sau đó, nó gọi sang Module Notification để bắn Push Notification / Email rầm rộ:
    // "Ting Ting! Món đồ bạn hằng mong ước [Tên SP] vừa được giảm giá kịch sàn. Mua ngay kẻo lỡ!"
}