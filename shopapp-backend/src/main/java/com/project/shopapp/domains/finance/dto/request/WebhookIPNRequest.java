// --- request/WebhookIPNRequest.java ---
package com.project.shopapp.domains.finance.dto.request;
import lombok.Data;
import java.util.Map;

@Data
public class WebhookIPNRequest {
    // DTO này hứng dữ liệu dạng Map linh hoạt vì mỗi cổng (Momo, VNPAY, ZaloPay) bắn Payload khác nhau
    private Map<String, Object> payload;
}