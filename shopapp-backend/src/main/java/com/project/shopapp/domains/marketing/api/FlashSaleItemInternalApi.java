package com.project.shopapp.domains.marketing.api;
import com.project.shopapp.domains.marketing.dto.nested.FlashSaleItemBasicDto;

public interface FlashSaleItemInternalApi {
    // Trả về thông tin Giá Sale nếu Sản phẩm này ĐANG CÓ Flash Sale Active
    FlashSaleItemBasicDto getActiveFlashSaleInfo(Integer productId, Integer variantId);

    // Gọi khi Checkout: Cố gắng trừ tồn kho Flash Sale. Trả về True nếu thành công.
    boolean consumeFlashSaleStock(Long flashSaleItemId, int qty);

    // Gọi khi Hủy đơn: Trả lại tồn kho
    void releaseFlashSaleStock(Long flashSaleItemId, int qty);
}