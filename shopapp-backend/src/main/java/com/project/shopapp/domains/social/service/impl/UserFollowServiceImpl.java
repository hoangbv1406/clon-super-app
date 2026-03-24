// --- service/impl/UserFollowServiceImpl.java ---
package com.project.shopapp.domains.social.service.impl;

import com.project.shopapp.domains.social.constant.SocialConstants;
import com.project.shopapp.domains.social.dto.nested.FollowCountDto;
import com.project.shopapp.domains.social.dto.request.FollowRequest;
import com.project.shopapp.domains.social.dto.response.FollowResponse;
import com.project.shopapp.domains.social.entity.UserFollow;
import com.project.shopapp.domains.social.enums.FollowType;
import com.project.shopapp.domains.social.event.NewFollowerEvent;
import com.project.shopapp.domains.social.repository.UserFollowRepository;
import com.project.shopapp.domains.social.service.UserFollowService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFollowServiceImpl implements UserFollowService {

    private final UserFollowRepository followRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void follow(Integer followerId, FollowRequest request) {
        // 1. Chống Bot Spam: 1 User không được theo dõi quá 5.000 người/shop
        if (followRepo.countByFollowerIdAndIsDeleted(followerId, 0L) >= SocialConstants.MAX_FOLLOWING_LIMIT) {
            throw new ConflictException("Bạn đã đạt giới hạn theo dõi " + SocialConstants.MAX_FOLLOWING_LIMIT + " người/shop.");
        }

        FollowType type = FollowType.valueOf(request.getFollowType().toUpperCase());
        Optional<UserFollow> existingFollow;

        // 2. Tìm kiếm hành động cũ
        if (type == FollowType.USER) {
            if (followerId.equals(request.getTargetUserId())) throw new ConflictException("Không thể tự theo dõi chính mình");
            existingFollow = followRepo.findByFollowerIdAndFollowingUserIdAndIsDeleted(followerId, request.getTargetUserId(), 0L);
        } else {
            existingFollow = followRepo.findByFollowerIdAndFollowingShopIdAndIsDeleted(followerId, request.getTargetShopId(), 0L);
        }

        if (existingFollow.isPresent()) {
            throw new ConflictException("Bạn đã theo dõi đối tượng này rồi.");
        }

        // 3. Xử lý Soft Delete Recovery (Nếu unfollow rồi follow lại)
        // (Ở mức nâng cao: Ta query cả những record is_deleted > 0 và Update lại = 0. Tạm thời tạo mới)

        UserFollow follow = UserFollow.builder()
                .followerId(followerId)
                .followingUserId(type == FollowType.USER ? request.getTargetUserId() : null)
                .followingShopId(type == FollowType.SHOP ? request.getTargetShopId() : null)
                .build();

        followRepo.save(follow);

        // 4. Bắn Event Push Noti
        eventPublisher.publishEvent(new NewFollowerEvent(followerId, type, type == FollowType.USER ? request.getTargetUserId() : request.getTargetShopId()));
    }

    @Override
    @Transactional
    public void unfollow(Integer followerId, FollowRequest request) {
        FollowType type = FollowType.valueOf(request.getFollowType().toUpperCase());
        Optional<UserFollow> existingFollow;

        if (type == FollowType.USER) {
            existingFollow = followRepo.findByFollowerIdAndFollowingUserIdAndIsDeleted(followerId, request.getTargetUserId(), 0L);
        } else {
            existingFollow = followRepo.findByFollowerIdAndFollowingShopIdAndIsDeleted(followerId, request.getTargetShopId(), 0L);
        }

        existingFollow.ifPresent(follow -> {
            follow.setIsDeleted(System.currentTimeMillis()); // Soft Delete
            followRepo.save(follow);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public FollowCountDto getUserFollowStats(Integer userId) {
        long following = followRepo.countByFollowerIdAndIsDeleted(userId, 0L);
        long followers = followRepo.countByFollowingUserIdAndIsDeleted(userId, 0L);
        return new FollowCountDto(followers, following);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FollowResponse> getMyFollowingList(Integer followerId, int page, int size) {
        Page<UserFollow> pagedResult = followRepo.findByFollowerIdAndIsDeleted(
                followerId, 0L, PageRequest.of(page - 1, size, Sort.by("createdAt").descending()));

        return PageResponse.of(pagedResult.map(f -> {
            // Thủ công đắp dữ liệu DTO để logic linh hoạt
            return FollowResponse.builder()
                    .id(f.getId())
                    .followerId(f.getFollowerId())
                    .followType(f.getFollowingShopId() != null ? "SHOP" : "USER")
                    .targetId(f.getFollowingShopId() != null ? f.getFollowingShopId() : f.getFollowingUserId())
                    // TODO: Gọi API sang module Identity hoặc Vendor để lấy Tên và Avatar đắp vào đây
                    .targetName("Tên Đối Tượng")
                    .createdAt(f.getCreatedAt())
                    .build();
        }));
    }
}