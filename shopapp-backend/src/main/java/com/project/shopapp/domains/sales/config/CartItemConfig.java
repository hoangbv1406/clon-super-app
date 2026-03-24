package com.project.shopapp.domains.sales.config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartItemConfig {
    // TODO: Template. Trong tương lai, việc đếm (COUNT) số lượng badge hiển thị trên giỏ hàng góc phải màn hình
    // sẽ được Cache vào Redis để tránh gọi Count() vào DB mỗi lần load trang.
    // Bean RedisCacheManager sẽ được đặt ở đây.
}