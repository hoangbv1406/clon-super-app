package com.project.shopapp.domains.pos.api.impl;

import com.project.shopapp.domains.pos.api.PosSessionInternalApi;
import com.project.shopapp.domains.pos.enums.PosSessionStatus;
import com.project.shopapp.domains.pos.repository.PosSessionRepository;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PosSessionInternalApiImpl implements PosSessionInternalApi {
    private final PosSessionRepository posRepo;

    @Override
    public Integer getActiveSessionIdForUser(Integer shopId, Integer userId) {
        return posRepo.findByShopIdAndUserIdAndStatus(shopId, userId, PosSessionStatus.OPEN)
                .orElseThrow(() -> new ForbiddenException("Bạn phải Mở Ca Làm Việc trước khi tạo đơn hàng POS"))
                .getId();
    }
}
