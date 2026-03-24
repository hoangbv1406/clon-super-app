// --- api/impl/UserInteractionInternalApiImpl.java ---
package com.project.shopapp.domains.social.api.impl;

import com.project.shopapp.domains.social.api.UserInteractionInternalApi;
import com.project.shopapp.domains.social.repository.UserInteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInteractionInternalApiImpl implements UserInteractionInternalApi {

    private final UserInteractionRepository interactionRepo;

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserLikedPost(Integer userId, Long postId) {
        if (userId == null) return false;

        // TODO: Enterprise System sẽ check trong Redis trước: SISMEMBER post:{postId}:likes {userId}
        // Nếu không có Redis, fallback về Query Database:
        String latestAction = interactionRepo.findLatestLikeActionStatus(userId, postId);
        return "LIKE".equals(latestAction);
    }
}