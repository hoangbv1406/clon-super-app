// --- request/CartMergeRequest.java ---
package com.project.shopapp.domains.sales.dto.request;
import com.project.shopapp.domains.sales.validation.ValidSessionId;
import lombok.Data;

@Data
public class CartMergeRequest {
    @ValidSessionId
    private String sessionId; // ID của Session vãng lai cần gộp vào User thật
}