package com.project.shopapp.domains.social.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.social.dto.nested.FollowCountDto;
import com.project.shopapp.domains.social.dto.request.FollowRequest;
import com.project.shopapp.domains.social.dto.response.FollowResponse;
import com.project.shopapp.domains.social.service.UserFollowService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/social/follows")
@RequiredArgsConstructor
public class UserFollowController {

    private final UserFollowService followService;
    private final SecurityUtils securityUtils;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> follow(@Valid @RequestBody FollowRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        followService.follow(userId, request);
        return ResponseEntity.ok(ResponseObject.created(null, "Đã theo dõi"));
    }

    @PostMapping("/unfollow") // Dùng POST body thay vì DELETE để truyền JSON rõ ràng
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> unfollow(@Valid @RequestBody FollowRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        followService.unfollow(userId, request);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã bỏ theo dõi"));
    }

    @GetMapping("/mine/following")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<PageResponse<FollowResponse>>> getMyFollowingList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(followService.getMyFollowingList(userId, page, size)));
    }

    // PUBLIC: Xem Profile của KOC (Xem được họ có bao nhiêu Followers)
    @GetMapping("/users/{targetUserId}/stats")
    public ResponseEntity<ResponseObject<FollowCountDto>> getUserFollowStats(@PathVariable Integer targetUserId) {
        return ResponseEntity.ok(ResponseObject.success(followService.getUserFollowStats(targetUserId)));
    }
}