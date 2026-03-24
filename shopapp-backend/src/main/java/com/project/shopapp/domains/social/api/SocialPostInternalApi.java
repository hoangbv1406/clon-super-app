// --- api/SocialPostInternalApi.java ---
package com.project.shopapp.domains.social.api;

public interface SocialPostInternalApi {
    void adjustLikeCount(Long postId, boolean isLike);
    void increaseCommentCount(Long postId);
    void increaseShareCount(Long postId);
}