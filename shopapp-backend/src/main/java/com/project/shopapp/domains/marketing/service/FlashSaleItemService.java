// --- service/FlashSaleItemService.java ---
package com.project.shopapp.domains.marketing.service;
import com.project.shopapp.domains.marketing.dto.request.FlashSaleItemCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleItemResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.util.List;

public interface FlashSaleItemService {
    PageResponse<FlashSaleItemResponse> getItemsByFlashSale(Integer flashSaleId, int page, int size);
    List<FlashSaleItemResponse> addItemsToFlashSale(Integer adminId, Integer flashSaleId, List<FlashSaleItemCreateRequest> requests);
    void removeItemFromFlashSale(Integer adminId, Long itemId);
}