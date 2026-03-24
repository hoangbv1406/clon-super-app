// --- service/impl/CartServiceImpl.java ---
package com.project.shopapp.domains.sales.service.impl;

import com.project.shopapp.domains.sales.api.CartInternalApi;
import com.project.shopapp.domains.sales.dto.response.CartResponse;
import com.project.shopapp.domains.sales.entity.Cart;
import com.project.shopapp.domains.sales.enums.CartStatus;
import com.project.shopapp.domains.sales.event.CartMergedEvent;
import com.project.shopapp.domains.sales.mapper.CartMapper;
import com.project.shopapp.domains.sales.repository.CartRepository;
import com.project.shopapp.domains.sales.service.CartService;
import com.project.shopapp.domains.sales.specification.CartSpecification;
import com.project.shopapp.shared.base.PageResponse;
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
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final CartMapper cartMapper;
    private final CartInternalApi cartApi;
    private final ApplicationEventPublisher eventPublisher;

    // TODO: Sẽ inject CartItemInternalApi ở bài sau để tính Tổng lượng & Tổng tiền

    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart(Integer userId, String sessionId) {
        Cart cart = null;
        if (userId != null) {
            cart = cartRepo.findByUserIdAndIsDeleted(userId, 0L).orElse(null);
        } else if (sessionId != null) {
            cart = cartRepo.findBySessionIdAndIsDeleted(sessionId, 0L).orElse(null);
        }

        if (cart == null) {
            return CartResponse.builder().totalItems(0).build(); // Trả về giỏ rỗng
        }

        CartResponse response = cartMapper.toDto(cart);
        // Computed fields
        response.setTotalItems(0); // TODO: Sẽ tích hợp API lấy Count từ cart_items
        return response;
    }

    @Override
    @Transactional
    public void mergeGuestCartToUser(Integer userId, String sessionId) {
        if (sessionId == null) return;

        Optional<Cart> guestCartOpt = cartRepo.findBySessionIdAndIsDeleted(sessionId, 0L);
        if (guestCartOpt.isEmpty()) return;

        Cart guestCart = guestCartOpt.get();
        Integer userCartId = cartApi.getOrProvisionCartId(userId, null);

        // TODO: Chuyển toàn bộ CartItems từ guestCartId sang userCartId (Cộng dồn số lượng nếu trùng)

        // Hủy giỏ Guest
        guestCart.setIsDeleted(System.currentTimeMillis());
        cartRepo.save(guestCart);

        eventPublisher.publishEvent(new CartMergedEvent(userId, sessionId, userCartId));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CartResponse> getCartsForAdmin(String statusStr, Boolean isGuest, int page, int size) {
        CartStatus status = statusStr != null ? CartStatus.valueOf(statusStr.toUpperCase()) : null;
        Page<Cart> pagedResult = cartRepo.findAll(
                CartSpecification.filterCarts(status, isGuest),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(cartMapper::toDto));
    }
}