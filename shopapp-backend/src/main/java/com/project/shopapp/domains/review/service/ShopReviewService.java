package com.project.shopapp.domains.review.service;

import com.project.shopapp.domains.review.dto.request.ShopReviewCreateRequest;
import com.project.shopapp.domains.review.dto.request.ShopReviewReplyRequest;
import com.project.shopapp.domains.review.dto.response.ShopReviewResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface ShopReviewService {
    ShopReviewResponse createReview(Integer userId, Integer shopId, ShopReviewCreateRequest request);

    ShopReviewResponse replyToReview(Integer currentUserId, Integer shopId, Long parentReviewId, ShopReviewReplyRequest request);

    PageResponse<ShopReviewResponse> getShopReviews(Integer shopId, Byte rating, Boolean hasImages, int page, int size);
}