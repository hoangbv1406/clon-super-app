// --- api/impl/SocialPostInternalApiImpl.java ---
package com.project.shopapp.domains.social.api.impl;

import com.project.shopapp.domains.social.api.SocialPostInternalApi;
import com.project.shopapp.domains.social.repository.SocialPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialPostInternalApiImpl implements SocialPostInternalApi {

    private final SocialPostRepository postRepo;

    @Override
    @Transactional
    public void adjustLikeCount(Long postId, boolean isLike) {
        if (isLike) {
            postRepo.incrementLikes(postId);
        } else {
            postRepo.decrementLikes(postId);
        }
    }

    @Override
    @Transactional
    public void increaseCommentCount(Long postId) {
        postRepo.incrementComments(postId);
    }

    @Override
    @Transactional
    public void increaseShareCount(Long postId) {
        // Tương tự, gọi postRepo.incrementShares(postId);
    }
}