// --- service/impl/FavoriteServiceImpl.java ---
package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.domains.catalog.dto.request.FavoriteRequest;
import com.project.shopapp.domains.catalog.dto.response.FavoriteResponse;
import com.project.shopapp.domains.catalog.entity.Favorite;
import com.project.shopapp.domains.catalog.event.ProductFavoritedEvent;
import com.project.shopapp.domains.catalog.mapper.FavoriteMapper;
import com.project.shopapp.domains.catalog.repository.FavoriteRepository;
import com.project.shopapp.domains.catalog.service.FavoriteService;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepo;
    private final FavoriteMapper favoriteMapper;
    private final ProductInternalApi productApi; // Lấy thông tin SP
    private final ApplicationEventPublisher eventPublisher;

    private static final int MAX_FAVORITES_PER_USER = 500; // Enterprise limit

    @Override
    @Transactional
    public void toggleFavorite(Integer userId, FavoriteRequest request) {
        Integer productId = request.getProductId();

        // Đảm bảo Product tồn tại
        ProductBasicDto product = productApi.getProductBasicInfo(productId);
        if (product == null) throw new DataNotFoundException("Sản phẩm không tồn tại");

        Optional<Favorite> existing = favoriteRepo.findByUserIdAndProductIdAndIsDeleted(userId, productId, 0L);

        if (existing.isPresent()) {
            // Đã thích -> Bấm lần nữa là HỦY THÍCH (Unlike)
            Favorite fav = existing.get();
            fav.setIsDeleted(System.currentTimeMillis());
            favoriteRepo.save(fav);
            eventPublisher.publishEvent(new ProductFavoritedEvent(userId, productId, false));
        } else {
            // Chưa thích -> Lưu mới
            if (favoriteRepo.countByUserIdAndIsDeleted(userId, 0L) >= MAX_FAVORITES_PER_USER) {
                throw new ConflictException("Danh sách yêu thích đã đầy (Tối đa " + MAX_FAVORITES_PER_USER + " sản phẩm).");
            }

            Favorite fav = Favorite.builder()
                    .userId(userId)
                    .productId(productId)
                    .build();
            favoriteRepo.save(fav);
            eventPublisher.publishEvent(new ProductFavoritedEvent(userId, productId, true));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FavoriteResponse> getMyFavorites(Integer userId, int page, int size) {
        Page<Favorite> pagedResult = favoriteRepo.findByUserIdAndIsDeleted(
                userId, 0L, PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );

        return PageResponse.of(pagedResult.map(fav -> {
            FavoriteResponse dto = favoriteMapper.toDto(fav);

            // Đắp (Hydrate) thông tin sản phẩm vào
            ProductBasicDto prod = productApi.getProductBasicInfo(fav.getProductId());
            if (prod != null) {
                dto.setProductName(prod.getName());
                dto.setProductPrice(prod.getPrice().toString());
                dto.setProductThumbnail(prod.getThumbnail());
                dto.setProductSlug(prod.getSlug());
                dto.setIsOutOfStock(prod.getStockQuantity() <= 0); // Báo khách biết hàng đã hết
            }
            return dto;
        }));
    }
}