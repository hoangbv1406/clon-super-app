// --- api/impl/UserFollowInternalApiImpl.java ---
package com.project.shopapp.domains.social.api.impl;

import com.project.shopapp.domains.social.api.UserFollowInternalApi;
import com.project.shopapp.domains.social.repository.UserFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFollowInternalApiImpl implements UserFollowInternalApi {

    private final UserFollowRepository followRepo;

    @Override
    @Transactional(readOnly = true)
    public boolean isUserFollowingShop(Integer userId, Integer shopId) {
        if (userId == null) return false;
        return followRepo.findByFollowerIdAndFollowingShopIdAndIsDeleted(userId, shopId, 0L).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public long getShopFollowerCount(Integer shopId) {
        // TODO: Enterprise sẽ lấy từ Redis Cache thay vì COUNT trực tiếp DB mỗi lần tải trang Shop
        return followRepo.countByFollowingShopIdAndIsDeleted(shopId, 0L);
    }
}