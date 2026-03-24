// --- service/FlashSaleService.java ---
package com.project.shopapp.domains.marketing.service;
import com.project.shopapp.domains.marketing.dto.request.FlashSaleCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface FlashSaleService {
    PageResponse<FlashSaleResponse> searchSales(Integer shopId, String status, int page, int size);
    FlashSaleResponse createFlashSale(Integer currentUserId, Integer shopId, FlashSaleCreateRequest request);
    void cancelFlashSale(Integer currentUserId, Integer flashSaleId);
}