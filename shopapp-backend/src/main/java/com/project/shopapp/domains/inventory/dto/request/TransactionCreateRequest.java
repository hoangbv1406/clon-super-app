package com.project.shopapp.domains.inventory.dto.request;
import com.project.shopapp.domains.inventory.validation.ValidTransactionType;
import lombok.Data;

@Data
public class TransactionCreateRequest {
    @ValidTransactionType
    private String type;

    private String referenceType;
    private Long referenceId;
    private String note;

    // Lưu ý: Sẽ có một List<TransactionDetailRequest> được gửi kèm ở bài sau,
    // nhưng ở mức độ Header, ta chỉ cần các trường này.
}