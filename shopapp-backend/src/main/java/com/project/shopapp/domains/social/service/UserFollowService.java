// --- service/UserFollowService.java ---
package com.project.shopapp.domains.social.service;
import com.project.shopapp.domains.social.dto.nested.FollowCountDto;
import com.project.shopapp.domains.social.dto.request.FollowRequest;
import com.project.shopapp.domains.social.dto.response.FollowResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface UserFollowService {
    void follow(Integer followerId, FollowRequest request);
    void unfollow(Integer followerId, FollowRequest request);
    FollowCountDto getUserFollowStats(Integer userId);
    PageResponse<FollowResponse> getMyFollowingList(Integer followerId, int page, int size);
}