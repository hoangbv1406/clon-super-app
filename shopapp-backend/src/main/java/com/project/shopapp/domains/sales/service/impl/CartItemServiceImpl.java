// --- service/impl/CartItemServiceImpl.java ---
package com.project.shopapp.domains.sales.service.impl;

import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.domains.sales.api.CartInternalApi;
import com.project.shopapp.domains.sales.dto.request.CartItemAddRequest;
import com.project.shopapp.domains.sales.dto.request.CartItemUpdateRequest;
import com.project.shopapp.domains.sales.dto.response.CartItemResponse;
import com.project.shopapp.domains.sales.dto.response.CartResponse;
import com.project.shopapp.domains.sales.entity.CartItem;
import com.project.shopapp.domains.sales.event.CartItemUpdatedEvent;
import com.project.shopapp.domains.sales.mapper.CartItemMapper;
import com.project.shopapp.domains.sales.repository.CartItemRepository;
import com.project.shopapp.domains.sales.service.CartItemService;
import com.project.shopapp.domains.sales.service.CartService;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository itemRepo;
    private final CartItemMapper itemMapper;
    private final CartInternalApi cartApi;
    private final CartService cartService; // Gọi chéo cùng module
    private final ProductInternalApi productApi;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getFullCart(Integer userId, String sessionId) {
        CartResponse cartData = cartService.getMyCart(userId, sessionId);
        if (cartData.getId() == null) return cartData; // Giỏ rỗng

        List<CartItem> items = itemRepo.findByCartId(cartData.getId());

        BigDecimal totalCartPrice = BigDecimal.ZERO;
        int totalCartItems = 0;

        // Dùng vòng lặp for truyền thống để vừa Hydrate vừa tính tổng an toàn
        List<CartItemResponse> itemResponses = new java.util.ArrayList<>();

        for (CartItem entity : items) {
            CartItemResponse dto = itemMapper.toDto(entity);

            // Gọi Catalog API
            ProductBasicDto prod = productApi.getProductBasicInfoForCart(entity.getProductId(), entity.getVariantId());
            if (prod != null) {
                dto.setProductName(prod.getName());
                dto.setVariantName(prod.getVariantName());
                dto.setImageUrl(prod.getThumbnail());
                dto.setCurrentPrice(prod.getPrice());
                dto.setCurrentStock(prod.getStockQuantity());

                // Business Logic: Cảnh báo giá và Hết hàng
                dto.setIsPriceChanged(entity.getPriceAtAdd().compareTo(prod.getPrice()) != 0);
                dto.setIsOutOfStock(prod.getStockQuantity() < entity.getQuantity());

                dto.setLineTotal(prod.getPrice().multiply(new BigDecimal(entity.getQuantity())));

                // CỘNG DỒN GIÁ TIỀN (Gán lại biến mới chuẩn của BigDecimal)
                if (Boolean.TRUE.equals(entity.getIsSelected()) && !dto.getIsOutOfStock()) {
                    totalCartPrice = totalCartPrice.add(dto.getLineTotal());
                }
            } else {
                dto.setIsOutOfStock(true);
            }

            // CỘNG DỒN SỐ LƯỢNG
            totalCartItems += entity.getQuantity();

            itemResponses.add(dto);
        }

        cartData.setTotalItems(totalCartItems);
        cartData.setTotalPrice(totalCartPrice);

        return cartData;
    }

    @Override
    @Transactional
    public void addItem(Integer userId, String sessionId, CartItemAddRequest request) {
        Integer cartId = cartApi.getOrProvisionCartId(userId, sessionId);

        // Lấy giá TẠI THỜI ĐIỂM BỎ VÀO GIỎ
        BigDecimal currentPrice = productApi.getCurrentPrice(request.getProductId(), request.getVariantId());
        if (currentPrice == null) throw new DataNotFoundException("Sản phẩm không khả dụng");

        Optional<CartItem> existingItem = itemRepo.findByCartIdAndProductIdAndVariantId(cartId, request.getProductId(), request.getVariantId());

        if (existingItem.isPresent()) {
            itemRepo.incrementQuantity(existingItem.get().getId(), request.getQuantity());
            // Cập nhật lại giá add_at phòng trường hợp giá mới đổi
            existingItem.get().setPriceAtAdd(currentPrice);
            itemRepo.save(existingItem.get());
        } else {
            CartItem newItem = itemMapper.toEntityFromRequest(request);
            newItem.setCartId(cartId);
            newItem.setPriceAtAdd(currentPrice);
            itemRepo.save(newItem);
        }

        eventPublisher.publishEvent(new CartItemUpdatedEvent(cartId, request.getProductId(), "ADDED"));
    }

    @Override
    @Transactional
    public void updateItem(Integer userId, String sessionId, Integer itemId, CartItemUpdateRequest request) {
        Integer cartId = cartApi.getOrProvisionCartId(userId, sessionId);
        CartItem item = itemRepo.findById(itemId).orElseThrow(() -> new DataNotFoundException("Item không tồn tại"));

        if (!item.getCartId().equals(cartId)) throw new SecurityException("Không có quyền");

        if (request.getQuantity() != null) item.setQuantity(request.getQuantity());
        if (request.getIsSelected() != null) item.setIsSelected(request.getIsSelected());

        itemRepo.save(item);
    }

    @Override
    @Transactional
    public void removeItem(Integer userId, String sessionId, Integer itemId) {
        Integer cartId = cartApi.getOrProvisionCartId(userId, sessionId);
        CartItem item = itemRepo.findById(itemId).orElseThrow();
        if (item.getCartId().equals(cartId)) {
            itemRepo.delete(item);
            eventPublisher.publishEvent(new CartItemUpdatedEvent(cartId, item.getProductId(), "REMOVED"));
        }
    }

    @Override
    @Transactional
    public void toggleSelectAll(Integer userId, String sessionId, boolean isSelected) {
        Integer cartId = cartApi.getOrProvisionCartId(userId, sessionId);
        itemRepo.updateSelectAll(cartId, isSelected);
    }
}