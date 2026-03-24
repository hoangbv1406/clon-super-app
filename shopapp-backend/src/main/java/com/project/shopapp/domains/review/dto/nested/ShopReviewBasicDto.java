package com.project.shopapp.domains.review.dto.nested;
import lombok.Data;

@Data
public class ShopReviewBasicDto {
    private Long id;
    private Byte rating;
    private String content;
}