// --- request/ShowcaseReorderRequest.java ---
package com.project.shopapp.domains.affiliate.dto.request;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class ShowcaseReorderRequest {
    @NotEmpty(message = "Danh sách ID không được rỗng")
    private List<Integer> showcaseItemIds; // Frontend gửi mảng ID theo thứ tự KOC vừa kéo thả (Drag & Drop)
}