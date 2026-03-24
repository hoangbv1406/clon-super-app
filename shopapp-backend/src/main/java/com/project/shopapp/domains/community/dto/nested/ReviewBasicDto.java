// --- nested/ReviewBasicDto.java ---
package com.project.shopapp.domains.community.dto.nested;
import lombok.Data;

@Data
public class ReviewBasicDto {
    private Integer ratingAvg;
    private Integer totalReviews;
    // Dùng để module Catalog gọi sang lấy số liệu thống kê hiển thị lên Product
}