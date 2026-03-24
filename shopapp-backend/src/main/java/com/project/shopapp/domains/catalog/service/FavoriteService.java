// --- service/FavoriteService.java ---
package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.request.FavoriteRequest;
import com.project.shopapp.domains.catalog.dto.response.FavoriteResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface FavoriteService {
    void toggleFavorite(Integer userId, FavoriteRequest request);
    PageResponse<FavoriteResponse> getMyFavorites(Integer userId, int page, int size);
}