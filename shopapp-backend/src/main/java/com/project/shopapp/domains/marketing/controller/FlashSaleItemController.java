package com.project.shopapp.domains.marketing.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.marketing.dto.request.FlashSaleItemCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleItemResponse;
import com.project.shopapp.domains.marketing.service.FlashSaleItemService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FlashSaleItemController {

    private final FlashSaleItemService itemService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Lấy danh sách sản phẩm đang sale của 1 đợt Flash Sale cụ thể
    @GetMapping("/flash-sales/{flashSaleId}/items")
    public ResponseEntity<ResponseObject<PageResponse<FlashSaleItemResponse>>> getItems(
            @PathVariable Integer flashSaleId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(ResponseObject.success(itemService.getItemsByFlashSale(flashSaleId, page, size)));
    }

    // VENDOR / ADMIN: Thêm hàng loạt sản phẩm vào đợt Sale
    @PostMapping("/vendor/flash-sales/{flashSaleId}/items/batch")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<List<FlashSaleItemResponse>>> addItemsBatch(
            @PathVariable Integer flashSaleId,
            @RequestBody List<@Valid FlashSaleItemCreateRequest> requests) {

        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                itemService.addItemsToFlashSale(userId, flashSaleId, requests),
                "Thêm sản phẩm vào Flash Sale thành công"
        ));
    }

    // VENDOR / ADMIN: Xóa 1 sản phẩm khỏi đợt Sale
    @DeleteMapping("/vendor/flash-sale-items/{itemId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<Void>> removeItem(@PathVariable Long itemId) {
        Integer userId = securityUtils.getLoggedInUserId();
        itemService.removeItemFromFlashSale(userId, itemId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã gỡ sản phẩm khỏi Flash Sale"));
    }
}