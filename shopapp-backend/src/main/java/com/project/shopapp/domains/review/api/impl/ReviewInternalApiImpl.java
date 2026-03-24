package com.project.shopapp.domains.review.api.impl;
import com.project.shopapp.domains.review.api.ReviewInternalApi;
import com.project.shopapp.domains.review.repository.ShopReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewInternalApiImpl implements ReviewInternalApi {
    private final ShopReviewRepository reviewRepo;

    @Override
    public long countTotalReviewsOfShop(Integer shopId) {
        // Có thể cache Redis ở đây
        return reviewRepo.count(); // Lược giản query count trong code mẫu
    }
}