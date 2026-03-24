package com.project.shopapp.domains.affiliate.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.affiliate.dto.request.ShowcaseAddRequest;
import com.project.shopapp.domains.affiliate.dto.request.ShowcaseReorderRequest;
import com.project.shopapp.domains.affiliate.dto.response.ShowcaseItemResponse;
import com.project.shopapp.domains.affiliate.service.UserShowcaseService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserShowcaseController {

    private final UserShowcaseService showcaseService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Bất cứ ai (kể cả Guest) cũng có thể xem tủ đồ của KOL
    @GetMapping("/public/showcases/users/{kocUserId}")
    public ResponseEntity<ResponseObject<PageResponse<ShowcaseItemResponse>>> getPublicShowcase(
            @PathVariable Integer kocUserId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(showcaseService.getPublicShowcase(kocUserId, page, size)));
    }

    // KOC: Quản lý tủ đồ của mình
    @GetMapping("/affiliates/showcases/mine")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<PageResponse<ShowcaseItemResponse>>> getMyShowcase(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(showcaseService.getMyShowcase(userId, page, size)));
    }

    @PostMapping("/affiliates/showcases/mine")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<ShowcaseItemResponse>> addItem(
            @Valid @RequestBody ShowcaseAddRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                showcaseService.addItemToShowcase(userId, request), "Đã thêm vào tủ đồ"
        ));
    }

    @PatchMapping("/affiliates/showcases/mine/reorder")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<Void>> reorderShowcase(
            @Valid @RequestBody ShowcaseReorderRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        showcaseService.reorderShowcase(userId, request);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã lưu vị trí hiển thị"));
    }

    @PatchMapping("/affiliates/showcases/mine/{itemId}/visibility")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<Void>> toggleVisibility(
            @PathVariable Integer itemId,
            @RequestParam boolean isHidden) {
        Integer userId = securityUtils.getLoggedInUserId();
        showcaseService.toggleItemVisibility(userId, itemId, isHidden);
        return ResponseEntity.ok(ResponseObject.success(null, isHidden ? "Đã ẩn sản phẩm" : "Đã hiện sản phẩm"));
    }

    @DeleteMapping("/affiliates/showcases/mine/{itemId}")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<Void>> removeItem(@PathVariable Integer itemId) {
        Integer userId = securityUtils.getLoggedInUserId();
        showcaseService.removeItemFromShowcase(userId, itemId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa khỏi tủ đồ"));
    }
}