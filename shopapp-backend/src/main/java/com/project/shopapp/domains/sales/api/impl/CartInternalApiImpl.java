// --- api/impl/CartInternalApiImpl.java ---
package com.project.shopapp.domains.sales.api.impl;

import com.project.shopapp.domains.sales.api.CartInternalApi;
import com.project.shopapp.domains.sales.entity.Cart;
import com.project.shopapp.domains.sales.enums.CartStatus;
import com.project.shopapp.domains.sales.repository.CartRepository;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartInternalApiImpl implements CartInternalApi {

    private final CartRepository cartRepo;

    @Override
    @Transactional
    public Integer getOrProvisionCartId(Integer userId, String sessionId) {
        if (userId != null) {
            Cart cart = cartRepo.findByUserIdAndIsDeleted(userId, 0L).orElseGet(() ->
                    cartRepo.save(Cart.builder().userId(userId).build())
            );
            checkActive(cart);
            return cart.getId();
        }

        if (sessionId != null) {
            Cart cart = cartRepo.findBySessionIdAndIsDeleted(sessionId, 0L).orElseGet(() ->
                    cartRepo.save(Cart.builder().sessionId(sessionId).expiresAt(LocalDateTime.now().plusDays(15)).build())
            );
            checkActive(cart);
            // Cập nhật lại hạn sử dụng (Keep-alive)
            cart.setExpiresAt(LocalDateTime.now().plusDays(15));
            cartRepo.save(cart);
            return cart.getId();
        }
        throw new IllegalArgumentException("Không xác định được danh tính (Thiếu UserId và SessionId)");
    }

    @Override
    @Transactional
    public void lockCart(Integer cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new DataNotFoundException("Giỏ hàng không tồn tại"));
        cart.setStatus(CartStatus.LOCKED);
        cartRepo.save(cart);
    }

    @Override
    @Transactional
    public void unlockCart(Integer cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow();
        cart.setStatus(CartStatus.ACTIVE);
        cartRepo.save(cart);
    }

    private void checkActive(Cart cart) {
        if (cart.getStatus() == CartStatus.LOCKED) {
            throw new ConflictException("Giỏ hàng đang bị khóa do đang trong quá trình thanh toán. Vui lòng hoàn tất hoặc hủy thanh toán trước.");
        }
    }
}