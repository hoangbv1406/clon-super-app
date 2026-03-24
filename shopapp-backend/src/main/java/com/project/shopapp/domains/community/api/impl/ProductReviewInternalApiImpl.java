// --- api/impl/ProductReviewInternalApiImpl.java ---
package com.project.shopapp.domains.community.api.impl;

import com.project.shopapp.domains.community.api.ProductReviewInternalApi;
import com.project.shopapp.domains.community.dto.nested.ReviewBasicDto;
import com.project.shopapp.domains.community.event.ProductRatingChangedEvent;
import com.project.shopapp.domains.community.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReviewInternalApiImpl implements ProductReviewInternalApi {

    private final ProductReviewRepository reviewRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public ReviewBasicDto getProductRatingStats(Integer productId) {
        ReviewBasicDto stats = new ReviewBasicDto();
        stats.setTotalReviews(reviewRepo.countApprovedReviewsByProduct(productId));
        stats.setRatingAvg(Math.round(reviewRepo.getAverageRatingByProduct(productId)));
        return stats;
    }

    @Override
    @Transactional
    public void recalculateAndPublishRating(Integer productId) {
        int count = reviewRepo.countApprovedReviewsByProduct(productId);
        float avg = reviewRepo.getAverageRatingByProduct(productId);

        // Bắn Event sang Module Catalog để nó update bảng `products`
        eventPublisher.publishEvent(new ProductRatingChangedEvent(productId, avg, count));
    }
}