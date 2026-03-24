// --- service/SocialPostService.java ---
package com.project.shopapp.domains.social.service;
import com.project.shopapp.domains.social.dto.request.PostCreateRequest;
import com.project.shopapp.domains.social.dto.response.PostResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface SocialPostService {
    PostResponse createPost(Integer userId, PostCreateRequest request);
    PageResponse<PostResponse> getTrendingFeed(Integer currentUserId, int page, int size);
    PageResponse<PostResponse> getUserWall(Integer currentUserId, Integer targetUserId, int page, int size);
    void deletePost(Integer userId, Long postId);
}