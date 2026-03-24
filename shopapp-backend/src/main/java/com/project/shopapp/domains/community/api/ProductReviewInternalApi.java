// --- api/ProductReviewInternalApi.java ---
package com.project.shopapp.domains.community.api;
import com.project.shopapp.domains.community.dto.nested.ReviewBasicDto;

public interface ProductReviewInternalApi {
    ReviewBasicDto getProductRatingStats(Integer productId);
    void recalculateAndPublishRating(Integer productId);
}