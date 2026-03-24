package com.project.shopapp.domains.marketing.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class FlashSaleResponse extends BaseResponse {
    private Integer id;
    private Integer shopId;
    private String name;
    private String coverImage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}