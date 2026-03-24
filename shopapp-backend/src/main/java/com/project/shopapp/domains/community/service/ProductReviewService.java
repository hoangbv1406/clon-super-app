// --- service/ProductReviewService.java ---
package com.project.shopapp.domains.community.service;
import com.project.shopapp.domains.community.dto.request.ReviewCreateRequest;
import com.project.shopapp.domains.community.dto.request.ReviewReplyRequest;
import com.project.shopapp.domains.community.dto.response.ReviewResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface ProductReviewService {
    ReviewResponse createReview(Integer userId, ReviewCreateRequest request);
    PageResponse<ReviewResponse> getProductReviews(Integer productId, Integer rating, Boolean hasImages, int page, int size);

    // Vendor
    ReviewResponse replyToReview(Integer shopId, Integer vendorUserId, Integer reviewId, ReviewReplyRequest request);

    // Public
    void markAsHelpful(Integer reviewId);
}