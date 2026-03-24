// --- service/impl/UserInteractionServiceImpl.java ---
package com.project.shopapp.domains.social.service.impl;

import com.project.shopapp.domains.social.api.SocialPostInternalApi;
import com.project.shopapp.domains.social.dto.request.InteractionLogRequest;
import com.project.shopapp.domains.social.entity.UserInteraction;
import com.project.shopapp.domains.social.enums.InteractionType;
import com.project.shopapp.domains.social.event.UserInteractedEvent;
import com.project.shopapp.domains.social.mapper.UserInteractionMapper;
import com.project.shopapp.domains.social.repository.UserInteractionRepository;
import com.project.shopapp.domains.social.service.UserInteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInteractionServiceImpl implements UserInteractionService {

    private final UserInteractionRepository interactionRepo;
    private final UserInteractionMapper interactionMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final SocialPostInternalApi postApi; // Gọi sang module Post để cộng điểm

    // Chạy BẤT ĐỒNG BỘ (@Async) để API trả về cho Frontend trong 5 mili-giây, 
    // không bắt App của khách phải chờ ghi Database.
    @Async("analyticsTaskExecutor")
    @Override
    @Transactional
    public void logInteraction(Integer userId, String ipAddress, InteractionLogRequest request) {
        InteractionType type = InteractionType.valueOf(request.getActionType().toUpperCase());

        UserInteraction interaction = interactionMapper.toEntityFromRequest(request);
        interaction.setUserId(userId != null ? userId : 0); // 0 = Guest
        interaction.setActionType(type);
        interaction.setIpAddress(ipAddress);

        interactionRepo.save(interaction);

        // Xử lý Business Logic cập nhật tổng số trực tiếp sang bảng liên quan
        if (request.getPostId() != null) {
            if (type == InteractionType.LIKE) {
                postApi.adjustLikeCount(request.getPostId(), true);
            } else if (type == InteractionType.UNLIKE) {
                postApi.adjustLikeCount(request.getPostId(), false);
            } else if (type == InteractionType.SHARE) {
                postApi.increaseShareCount(request.getPostId());
            }
        }

        // Bắn Event để hệ thống Data Pipeline (VD: Kafka -> Hadoop) hút dữ liệu vào Data Warehouse
        eventPublisher.publishEvent(new UserInteractedEvent(userId, request.getPostId(), request.getProductId(), type));
    }
}