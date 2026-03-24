package com.project.shopapp.domains.pos.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class PosSessionResponse extends BaseResponse {
    private Integer id;
    private Integer shopId;
    private Integer userId;
    private String cashierName; // Lấy từ User
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private BigDecimal openingCash;
    private BigDecimal closingCash;
    private BigDecimal expectedCash;
    private BigDecimal differenceCash;
    private String status;
    private String note;
}