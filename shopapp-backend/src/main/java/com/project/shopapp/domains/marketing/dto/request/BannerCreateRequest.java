package com.project.shopapp.domains.marketing.dto.request;
import com.project.shopapp.domains.marketing.validation.ValidBannerDateRange;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@ValidBannerDateRange // Custom validator ở mức Class
public class BannerCreateRequest {
    private String title;

    @NotBlank(message = "Link ảnh không được để trống")
    private String imageUrl;

    private String targetUrl;
    private String position;
    private String platform;
    private Integer displayOrder;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}