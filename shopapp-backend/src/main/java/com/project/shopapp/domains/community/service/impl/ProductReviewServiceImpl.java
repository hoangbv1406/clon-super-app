// --- service/impl/ProductReviewServiceImpl.java ---
package com.project.shopapp.domains.community.service.impl;

import com.project.shopapp.domains.community.api.ProductReviewInternalApi;
import com.project.shopapp.domains.community.dto.request.ReviewCreateRequest;
import com.project.shopapp.domains.community.dto.request.ReviewReplyRequest;
import com.project.shopapp.domains.community.dto.response.ReviewResponse;
import com.project.shopapp.domains.community.entity.ProductReview;
import com.project.shopapp.domains.community.enums.ReviewStatus;
import com.project.shopapp.domains.community.mapper.ProductReviewMapper;
import com.project.shopapp.domains.community.repository.ProductReviewRepository;
import com.project.shopapp.domains.community.service.ProductReviewService;
import com.project.shopapp.domains.community.specification.ProductReviewSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository reviewRepo;
    private final ProductReviewMapper reviewMapper;
    private final ProductReviewInternalApi reviewApi; // Để trigger tính lại sao

    @Override
    @Transactional
    public ReviewResponse createReview(Integer userId, ReviewCreateRequest request) {
        // 1. Kiểm tra 1 đơn hàng chỉ được review 1 lần
        if (reviewRepo.existsByOrderDetailIdAndIsDeleted(request.getOrderDetailId(), 0L)) {
            throw new ConflictException("Bạn đã đánh giá sản phẩm này rồi.");
        }

        // TODO: Ở hệ thống thật, cần gọi OrderDetailInternalApi để check xem:
        // 1. orderDetailId này có thuộc về userId không?
        // 2. OrderShop chứa món hàng này đã DELIVERED chưa? (Chưa nhận hàng cấm đánh giá).

        ProductReview review = reviewMapper.toEntityFromRequest(request);
        review.setUserId(userId);
        review.setStatus(ReviewStatus.APPROVED); // Tự động duyệt, nếu có từ khóa nhạy cảm thì cắm AI Filter vào đây.
        review.setCreatedBy(userId);

        ProductReview saved = reviewRepo.save(review);

        // 2. Tính toán lại số sao trung bình của Product và Bắn Event
        reviewApi.recalculateAndPublishRating(request.getProductId());

        return reviewMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getProductReviews(Integer productId, Integer rating, Boolean hasImages, int page, int size) {
        Page<ProductReview> pagedResult = reviewRepo.findAll(
                ProductReviewSpecification.filterForPublic(productId, rating, hasImages),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );

        return PageResponse.of(pagedResult.map(review -> {
            ReviewResponse dto = reviewMapper.toDto(review);

            // Tìm xem Shop có vào trả lời Review này không
            reviewRepo.findByParentIdAndIsDeleted(review.getId(), 0L)
                    .ifPresent(reply -> dto.setShopReply(reviewMapper.toDto(reply)));

            return dto;
        }));
    }

    @Override
    @Transactional
    public ReviewResponse replyToReview(Integer shopId, Integer vendorUserId, Integer reviewId, ReviewReplyRequest request) {
        ProductReview parentReview = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new DataNotFoundException("Đánh giá không tồn tại"));

        // TODO: Check quyền. Món hàng này (qua productId hoặc orderDetailId) phải thuộc về shopId.

        if (reviewRepo.findByParentIdAndIsDeleted(reviewId, 0L).isPresent()) {
            throw new ConflictException("Shop đã phản hồi đánh giá này rồi.");
        }

        ProductReview reply = ProductReview.builder()
                .productId(parentReview.getProductId())
                .parentId(parentReview.getId())
                .userId(vendorUserId) // Tài khoản nhân viên Shop
                .content(request.getContent())
                .status(ReviewStatus.APPROVED)
                .createdBy(vendorUserId)
                .build();

        return reviewMapper.toDto(reviewRepo.save(reply));
    }

    @Override
    @Transactional
    public void markAsHelpful(Integer reviewId) {
        reviewRepo.incrementHelpfulCount(reviewId);
    }
}