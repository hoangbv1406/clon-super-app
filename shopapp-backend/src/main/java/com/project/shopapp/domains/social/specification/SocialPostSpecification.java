package com.project.shopapp.domains.social.specification;

import com.project.shopapp.domains.social.entity.SocialPost;
import com.project.shopapp.domains.social.enums.PostStatus;
import org.springframework.data.jpa.domain.Specification;

public class SocialPostSpecification {
    // Generate Feed cho màn hình chủ
    public static Specification<SocialPost> generateTrendingFeed() {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("isDeleted"), 0L),
                cb.equal(root.get("status"), PostStatus.APPROVED)
        );
    }
}