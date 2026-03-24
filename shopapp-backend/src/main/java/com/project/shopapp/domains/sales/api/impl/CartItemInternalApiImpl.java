// --- api/impl/CartItemInternalApiImpl.java ---
package com.project.shopapp.domains.sales.api.impl;

import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.sales.api.CartItemInternalApi;
import com.project.shopapp.domains.sales.dto.nested.CartItemBasicDto;
import com.project.shopapp.domains.sales.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemInternalApiImpl implements CartItemInternalApi {

    private final CartItemRepository itemRepo;
    private final ProductInternalApi productApi; // Gọi sang module Catalog

    @Override
    @Transactional(readOnly = true)
    public List<CartItemBasicDto> getSelectedItemsForCheckout(Integer cartId) {
        return itemRepo.findByCartId(cartId).stream()
                .filter(item -> Boolean.TRUE.equals(item.getIsSelected()))
                .map(item -> {
                    CartItemBasicDto dto = new CartItemBasicDto();
                    dto.setProductId(item.getProductId());
                    dto.setVariantId(item.getVariantId());
                    dto.setQuantity(item.getQuantity());

                    // Chốt giá thực tế tại thời điểm Checkout, KHÔNG dùng priceAtAdd
                    dto.setCurrentPrice(productApi.getCurrentPrice(item.getProductId(), item.getVariantId()));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void clearSelectedItems(Integer cartId) {
        itemRepo.deleteSelectedItems(cartId);
    }
}