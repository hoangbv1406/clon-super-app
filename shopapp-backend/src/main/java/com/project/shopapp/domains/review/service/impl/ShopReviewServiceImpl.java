package com.project.shopapp.domains.review.service.impl;

import com.project.shopapp.domains.review.dto.request.ShopReviewCreateRequest;
import com.project.shopapp.domains.review.dto.request.ShopReviewReplyRequest;
import com.project.shopapp.domains.review.dto.response.ShopReviewResponse;
import com.project.shopapp.domains.review.entity.ShopReview;
import com.project.shopapp.domains.review.event.ShopReviewCreatedEvent;
import com.project.shopapp.domains.review.mapper.ShopReviewMapper;
import com.project.shopapp.domains.review.repository.ShopReviewRepository;
import com.project.shopapp.domains.review.service.ShopReviewService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopReviewServiceImpl implements ShopReviewService {

    private final ShopReviewRepository reviewRepository;
    private final ShopReviewMapper reviewMapper;
    private final ApplicationEventPublisher eventPublisher;

    // Giả lập FeignClient / Facade gọi sang Sales Module
    // private final OrderInternalApi orderApi;

    @Override
    @Transactional
    public ShopReviewResponse createReview(Integer userId, Integer shopId, ShopReviewCreateRequest request) {

        // 1. NGHIỆP VỤ: Gọi qua Sales Module kiểm tra đơn hàng có status = DELIVERED không?
        // if (!orderApi.isOrderDeliveredAndBelongsToUser(request.getOrderShopId(), userId, shopId)) {
        //     throw new ForbiddenException("Chỉ được đánh giá khi đơn hàng đã giao thành công");
        // }

        if (reviewRepository.existsByUserIdAndOrderShopIdAndIsDeleted(userId, request.getOrderShopId(), 0L)) {
            throw new ConflictException("Bạn đã đánh giá đơn hàng này rồi");
        }

        ShopReview review = reviewMapper.toEntityFromCreateRequest(request);
        review.setUserId(userId);
        review.setShopId(shopId);

        ShopReview saved = reviewRepository.save(review);

        // BẮN EVENT: Yêu cầu cập nhật điểm rating trung bình của Shop một cách bất đồng bộ
        eventPublisher.publishEvent(new ShopReviewCreatedEvent(shopId, request.getRating()));

        return reviewMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ShopReviewResponse replyToReview(Integer currentUserId, Integer shopId, Long parentReviewId, ShopReviewReplyRequest request) {
        // NGHIỆP VỤ: Check xem currentUserId có quyền trong Shop này không (Sử dụng ShopEmployeeInternalApi)
        // verifyShopPermission(currentUserId, shopId);

        ShopReview parentReview = reviewRepository.findById(parentReviewId)
                .filter(r -> r.getIsDeleted() == 0L && r.getParentId() == null)
                .orElseThrow(() -> new DataNotFoundException("Review gốc không tồn tại"));

        if (!parentReview.getShopId().equals(shopId)) {
            throw new ForbiddenException("Review này không thuộc về gian hàng của bạn");
        }

        // Tạo reply
        ShopReview reply = ShopReview.builder()
                .shopId(shopId)
                .userId(currentUserId) // Người đại diện shop trả lời
                .orderShopId(parentReview.getOrderShopId())
                .parentId(parentReview.getId())
                .content(request.getContent())
                .build();

        reviewRepository.save(reply);

        return reviewMapper.toDto(parentReview); // Trả về review gốc để FE tự fetch lại
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ShopReviewResponse> getShopReviews(Integer shopId, Byte rating, Boolean hasImages, int page, int size) {
        // Implement filter (Lược giản để tiết kiệm token)
        Page<ShopReview> reviewPage = reviewRepository.findByShopIdAndParentIdIsNullAndIsDeleted(
                shopId, 0L, PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );

        return PageResponse.of(reviewPage.map(review -> {
            ShopReviewResponse dto = reviewMapper.toDto(review);
            // Load Reply của Seller nếu có
            reviewRepository.findByParentIdAndIsDeleted(review.getId(), 0L)
                    .ifPresent(reply -> dto.setSellerReply(reviewMapper.toDto(reply)));
            return dto;
        }));
    }
}