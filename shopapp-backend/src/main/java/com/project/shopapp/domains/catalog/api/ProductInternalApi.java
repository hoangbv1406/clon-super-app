package com.project.shopapp.domains.catalog.api;

import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import java.math.BigDecimal;

public interface ProductInternalApi {
    // --- Các hàm kho cậu đang có ---
    boolean lockStock(Integer productId, Integer qty);
    void unlockStock(Integer productId, Integer qty);
    void commitStock(Integer productId, Integer qty);

    // --- [THÊM MỚI] Các hàm Hydration (Cung cấp data cho module khác) ---
    ProductBasicDto getProductBasicInfo(Integer productId); // <--- HÀM BỊ THIẾU GÂY LỖI SỐ 1

    ProductBasicDto getProductBasicInfoForCart(Integer productId, Integer variantId);

    BigDecimal getCurrentPrice(Integer productId, Integer variantId);
}