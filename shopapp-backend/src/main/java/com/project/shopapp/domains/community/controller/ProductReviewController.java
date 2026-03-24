package com.project.shopapp.domains.community.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.community.dto.request.ReviewCreateRequest;
import com.project.shopapp.domains.community.dto.request.ReviewReplyRequest;
import com.project.shopapp.domains.community.dto.response.ReviewResponse;
import com.project.shopapp.domains.community.service.ProductReviewService;
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
public class ProductReviewController {

    private final ProductReviewService reviewService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Bất cứ ai xem chi tiết sản phẩm cũng load được review
    @GetMapping("/public/products/{productId}/reviews")
    public ResponseEntity<ResponseObject<PageResponse<ReviewResponse>>> getReviews(
            @PathVariable Integer productId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Boolean hasImages,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(
                reviewService.getProductReviews(productId, rating, hasImages, page, size)
        ));
    }

    // PUBLIC: Bấm Hữu ích
    @PatchMapping("/public/reviews/{reviewId}/helpful")
    public ResponseEntity<ResponseObject<Void>> markHelpful(@PathVariable Integer reviewId) {
        reviewService.markAsHelpful(reviewId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã ghi nhận hữu ích"));
    }

    // USER: Viết đánh giá sau khi mua hàng
    @PostMapping("/community/reviews")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<ReviewResponse>> createReview(@Valid @RequestBody ReviewCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                reviewService.createReview(userId, request), "Đánh giá sản phẩm thành công"
        ));
    }

    // VENDOR: Chủ Shop vào phản hồi đánh giá của khách
    @PostMapping("/vendor/shops/{shopId}/product-reviews/{reviewId}/reply") // Thêm chữ product- vào đây
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'CSKH')")
    public ResponseEntity<ResponseObject<ReviewResponse>> replyReview(
            @PathVariable Integer shopId,
            @PathVariable Integer reviewId,
            @Valid @RequestBody ReviewReplyRequest request) {
        Integer vendorUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                reviewService.replyToReview(shopId, vendorUserId, reviewId, request), "Đã phản hồi khách hàng"
        ));
    }
}