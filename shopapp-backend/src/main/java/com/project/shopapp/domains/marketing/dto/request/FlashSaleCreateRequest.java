// --- request/FlashSaleCreateRequest.java ---
package com.project.shopapp.domains.marketing.dto.request;
import com.project.shopapp.domains.marketing.validation.ValidFlashSaleTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@ValidFlashSaleTime // Custom validator check logic Start < End
public class FlashSaleCreateRequest {
    @NotBlank(message = "Tên chương trình không được để trống")
    private String name;

    private String coverImage;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    private LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    private LocalDateTime endTime;
}