// --- api/impl/ShowcaseInternalApiImpl.java ---
package com.project.shopapp.domains.affiliate.api.impl;

import com.project.shopapp.domains.affiliate.api.ShowcaseInternalApi;
import com.project.shopapp.domains.affiliate.dto.request.ShowcaseAddRequest;
import com.project.shopapp.domains.affiliate.service.UserShowcaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowcaseInternalApiImpl implements ShowcaseInternalApi {

    private final UserShowcaseService showcaseService;

    @Override
    public void autoAddProductToShowcase(Integer kocUserId, Integer productId) {
        try {
            ShowcaseAddRequest req = new ShowcaseAddRequest();
            req.setProductId(productId);
            // Dùng lại service chính để chạy đủ logic (Tạo Affiliate Link, Check limit)
            showcaseService.addItemToShowcase(kocUserId, req);
        } catch (Exception e) {
            // Nuốt Exception. Nếu tự động add thất bại (do đầy tủ đồ) thì không làm chết luồng đăng Post
        }
    }
}