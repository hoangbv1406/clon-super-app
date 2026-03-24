package com.project.shopapp.domains.inventory.service;
import com.project.shopapp.domains.inventory.dto.request.ItemBatchCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.ProductItemResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.util.List;

public interface ProductItemService {
    PageResponse<ProductItemResponse> searchItems(Integer productId, Integer variantId, String imei, String status, int page, int size);
    List<ProductItemResponse> batchCreateItems(Integer adminId, ItemBatchCreateRequest request);
    ProductItemResponse markAsDefective(Integer adminId, Integer itemId);
}