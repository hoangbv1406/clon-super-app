package com.project.shopapp.domains.review.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.review.dto.request.ShopReviewCreateRequest;
import com.project.shopapp.domains.review.dto.request.ShopReviewReplyRequest;
import com.project.shopapp.domains.review.dto.response.ShopReviewResponse;
import com.project.shopapp.domains.review.service.ShopReviewService;
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
public class ShopReviewController {

    private final ShopReviewService reviewService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Xem đánh giá của một shop
    @GetMapping("/shops/{shopId}/reviews")
    public ResponseEntity<ResponseObject<PageResponse<ShopReviewResponse>>> getReviews(
            @PathVariable Integer shopId,
            @RequestParam(required = false) Byte rating,
            @RequestParam(required = false) Boolean hasImages,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ResponseObject.success(reviewService.getShopReviews(shopId, rating, hasImages, page, size)));
    }

    // USER: Viết đánh giá cho shop (Sau khi nhận hàng)
    @PostMapping("/shops/{shopId}/reviews")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<ShopReviewResponse>> createReview(
            @PathVariable Integer shopId,
            @Valid @RequestBody ShopReviewCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                reviewService.createReview(userId, shopId, request), "Cảm ơn bạn đã đánh giá!"
        ));
    }

    // VENDOR: Chủ shop trả lời đánh giá
    @PostMapping("/vendor/shops/{shopId}/reviews/{reviewId}/reply")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<ShopReviewResponse>> replyReview(
            @PathVariable Integer shopId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ShopReviewReplyRequest request) {
        Integer currentUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                reviewService.replyToReview(currentUserId, shopId, reviewId, request), "Phản hồi đánh giá thành công"
        ));
    }
}