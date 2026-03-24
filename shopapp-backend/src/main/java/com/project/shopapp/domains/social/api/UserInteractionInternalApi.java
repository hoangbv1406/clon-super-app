// --- api/UserInteractionInternalApi.java ---
package com.project.shopapp.domains.social.api;

public interface UserInteractionInternalApi {
    // Trả về TRUE nếu hành động cuối cùng của user là LIKE, FALSE nếu chưa từng tương tác hoặc UNLIKE.
    boolean hasUserLikedPost(Integer userId, Long postId);
}