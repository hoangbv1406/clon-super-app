package com.project.shopapp.domains.vendor.dto.nested;
import lombok.Data;

@Data
public class ShopBasicDto {
    private Integer id;
    private String name;
    private String slug;
    private String logoUrl;
    private Float ratingAvg;
    // Dùng để nhúng vào ProductResponse (khi xem sản phẩm, khách muốn biết SP này của Shop nào)
}