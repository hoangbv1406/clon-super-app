// --- service/UserShowcaseService.java ---
package com.project.shopapp.domains.affiliate.service;
import com.project.shopapp.domains.affiliate.dto.request.ShowcaseAddRequest;
import com.project.shopapp.domains.affiliate.dto.request.ShowcaseReorderRequest;
import com.project.shopapp.domains.affiliate.dto.response.ShowcaseItemResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface UserShowcaseService {
    ShowcaseItemResponse addItemToShowcase(Integer userId, ShowcaseAddRequest request);
    void removeItemFromShowcase(Integer userId, Integer itemId);
    void toggleItemVisibility(Integer userId, Integer itemId, boolean isHidden);
    void reorderShowcase(Integer userId, ShowcaseReorderRequest request);

    PageResponse<ShowcaseItemResponse> getMyShowcase(Integer userId, int page, int size);
    PageResponse<ShowcaseItemResponse> getPublicShowcase(Integer kocUserId, int page, int size);
}