// --- service/impl/SocialPostServiceImpl.java ---
package com.project.shopapp.domains.social.service.impl;

import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.domains.social.api.UserInteractionInternalApi; // Sẽ code ở bảng interactions
import com.project.shopapp.domains.social.dto.request.PostCreateRequest;
import com.project.shopapp.domains.social.dto.response.PostResponse;
import com.project.shopapp.domains.social.entity.SocialPost;
import com.project.shopapp.domains.social.enums.PostMediaType;
import com.project.shopapp.domains.social.enums.PostStatus;
import com.project.shopapp.domains.social.event.PostCreatedEvent;
import com.project.shopapp.domains.social.mapper.SocialPostMapper;
import com.project.shopapp.domains.social.repository.SocialPostRepository;
import com.project.shopapp.domains.social.service.SocialPostService;
import com.project.shopapp.domains.social.specification.SocialPostSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialPostServiceImpl implements SocialPostService {

    private final SocialPostRepository postRepo;
    private final SocialPostMapper postMapper;
    private final ProductInternalApi productApi;
    // private final UserInteractionInternalApi interactionApi; // Check xem user đã like chưa
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public PostResponse createPost(Integer userId, PostCreateRequest request) {
        SocialPost post = postMapper.toEntityFromRequest(request);
        post.setUserId(userId);
        post.setMediaType(PostMediaType.valueOf(request.getMediaType().toUpperCase()));
        post.setStatus(PostStatus.APPROVED); // Default duyệt tự động
        post.setCreatedBy(userId);

        // Kiểm tra xem SP gắn kèm có tồn tại không
        if (request.getLinkedProductId() != null) {
            ProductBasicDto prod = productApi.getProductBasicInfo(request.getLinkedProductId());
            if (prod == null) throw new DataNotFoundException("Sản phẩm gắn kèm không tồn tại");
        }

        SocialPost saved = postRepo.save(post);

        eventPublisher.publishEvent(new PostCreatedEvent(saved.getId(), userId));
        return hydratePost(saved, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PostResponse> getTrendingFeed(Integer currentUserId, int page, int size) {
        // Thuật toán đơn giản: Sort theo ID mới nhất (Sau này thay bằng điểm Trending = Like*2 + Cmt*3)
        Page<SocialPost> pagedResult = postRepo.findAll(
                SocialPostSpecification.generateTrendingFeed(),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(post -> hydratePost(post, currentUserId)));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PostResponse> getUserWall(Integer currentUserId, Integer targetUserId, int page, int size) {
        Page<SocialPost> pagedResult = postRepo.findByUserIdAndStatusAndIsDeleted(
                targetUserId, PostStatus.APPROVED.name(), 0L, PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(post -> hydratePost(post, currentUserId)));
    }

    @Override
    @Transactional
    public void deletePost(Integer userId, Long postId) {
        SocialPost post = postRepo.findById(postId).orElseThrow();
        if (!post.getUserId().equals(userId)) throw new ConflictException("Không có quyền xóa bài viết này");

        post.setIsDeleted(System.currentTimeMillis());
        postRepo.save(post);
    }

    // Hàm đắp dữ liệu bổ sung (Giỏ hàng, Trạng thái Like)
    private PostResponse hydratePost(SocialPost post, Integer currentUserId) {
        PostResponse dto = postMapper.toDto(post);

        // 1. Nếu có gắn giỏ hàng -> Gọi sang Catalog đắp thông tin SP vào
        if (post.getLinkedProductId() != null) {
            ProductBasicDto prod = productApi.getProductBasicInfo(post.getLinkedProductId());
            if (prod != null) {
                dto.setLinkedProductName(prod.getName());
                dto.setLinkedProductPrice(prod.getPrice().toString());
                dto.setLinkedProductThumbnail(prod.getThumbnail());
            }
        }

        // 2. Check xem User hiện tại mở App đã LIKE bài này chưa (Hiển thị trái tim đỏ hay xám)
        // dto.setIsLikedByCurrentUser(interactionApi.hasUserLikedPost(currentUserId, post.getId()));
        dto.setIsLikedByCurrentUser(false); // Tạm fix cứng, sẽ map sau khi code xong bảng Interactions

        return dto;
    }
}