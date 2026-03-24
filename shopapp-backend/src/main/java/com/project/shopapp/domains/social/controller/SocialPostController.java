package com.project.shopapp.domains.social.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.social.dto.request.PostCreateRequest;
import com.project.shopapp.domains.social.dto.response.PostResponse;
import com.project.shopapp.domains.social.service.SocialPostService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/social/posts")
@RequiredArgsConstructor
public class SocialPostController {

    private final SocialPostService postService;
    private final SecurityUtils securityUtils;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'KOC', 'VENDOR')")
    public ResponseEntity<ResponseObject<PostResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                postService.createPost(userId, request), "Đăng bài thành công"
        ));
    }

    // Load News Feed (Lướt Tiktok/Shopee Video)
    @GetMapping("/feed")
    public ResponseEntity<ResponseObject<PageResponse<PostResponse>>> getTrendingFeed(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Integer currentUserId = securityUtils.getLoggedInUserId(); // Khách chưa login vẫn xem được
        return ResponseEntity.ok(ResponseObject.success(
                postService.getTrendingFeed(currentUserId, page, size)
        ));
    }

    // Vào tường nhà của một người xem họ đăng gì
    @GetMapping("/wall/{targetUserId}")
    public ResponseEntity<ResponseObject<PageResponse<PostResponse>>> getUserWall(
            @PathVariable Integer targetUserId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Integer currentUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                postService.getUserWall(currentUserId, targetUserId, page, size)
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> deletePost(@PathVariable Long id) {
        Integer userId = securityUtils.getLoggedInUserId();
        postService.deletePost(userId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa bài viết"));
    }
}