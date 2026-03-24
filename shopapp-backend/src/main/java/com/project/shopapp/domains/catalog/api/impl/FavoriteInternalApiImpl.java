// --- api/impl/FavoriteInternalApiImpl.java ---
package com.project.shopapp.domains.catalog.api.impl;

import com.project.shopapp.domains.catalog.api.FavoriteInternalApi;
import com.project.shopapp.domains.catalog.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteInternalApiImpl implements FavoriteInternalApi {

    private final FavoriteRepository favoriteRepo;

    @Override
    @Transactional(readOnly = true)
    public boolean isProductFavoritedByUser(Integer userId, Integer productId) {
        if (userId == null) return false;
        return favoriteRepo.existsByUserIdAndProductIdAndIsDeleted(userId, productId, 0L);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getUsersWhoFavoritedProduct(Integer productId) {
        return favoriteRepo.findUserIdsByFavoritedProduct(productId);
    }
}