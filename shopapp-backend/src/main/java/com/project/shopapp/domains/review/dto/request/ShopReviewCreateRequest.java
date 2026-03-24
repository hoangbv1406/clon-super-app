package com.project.shopapp.domains.review.dto.request;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class ShopReviewCreateRequest {
    @NotNull(message = "ID Đơn hàng không được để trống")
    private Integer orderShopId;

    @NotNull(message = "Rating không được để trống")
    @Min(value = 1, message = "Rating thấp nhất là 1 sao")
    @Max(value = 5, message = "Rating cao nhất là 5 sao")
    private Byte rating;

    private String content;
    private List<String> images;
}