package com.project.shopapp.domains.marketing.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class BannerResponse extends BaseResponse {
    private Integer id;
    private String title;
    private String imageUrl;
    private String targetUrl;
    private String position;
    private String platform;
    private Integer displayOrder;
    private Integer clickCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
}