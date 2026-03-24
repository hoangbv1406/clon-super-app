// --- api/impl/OrderDetailInternalApiImpl.java ---
package com.project.shopapp.domains.sales.api.impl;

import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.domains.sales.api.OrderDetailInternalApi;
import com.project.shopapp.domains.sales.entity.CartItem;
import com.project.shopapp.domains.sales.entity.OrderDetail;
import com.project.shopapp.domains.sales.repository.CartItemRepository;
import com.project.shopapp.domains.sales.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailInternalApiImpl implements OrderDetailInternalApi {

    private final OrderDetailRepository detailRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductInternalApi productApi; // Module Catalog

    @Override
    @Transactional
    public void buildAndSaveDetailsForOrder(Long parentOrderId, Integer cartId) {
        // Lấy các item khách đã TICK trong giỏ
        List<CartItem> cartItems = cartItemRepo.findByCartId(cartId).stream()
                .filter(item -> Boolean.TRUE.equals(item.getIsSelected()))
                .collect(Collectors.toList());

        List<OrderDetail> details = cartItems.stream().map(cartItem -> {
            // LẤY SNAPSHOT (Tên, Ảnh, Giá, Bảo hành) NGAY LÚC NÀY
            ProductBasicDto snap = productApi.getProductBasicInfoForCart(cartItem.getProductId(), cartItem.getVariantId());

            BigDecimal totalMoney = snap.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            LocalDate warrantyDate = snap.getWarrantyPeriodMonths() != null
                    ? LocalDate.now().plusMonths(snap.getWarrantyPeriodMonths()) : null;

            return OrderDetail.builder()
                    .orderId(parentOrderId)
                    // .orderShopId(...) // TODO: Logic map vào OrderShop (Kiện hàng con) sẽ được xử lý ở Tầng OrderShopService
                    .productId(cartItem.getProductId())
                    .variantId(cartItem.getVariantId())
                    .price(snap.getPrice())
                    .numberOfProducts(cartItem.getQuantity())
                    .totalMoney(totalMoney)
                    // SNAPSHOT FIELDS: Sống chết mặc bay, bảng Product có đổi tên thì ở đây vẫn giữ tên cũ
                    .productName(snap.getName())
                    .variantName(snap.getVariantName())
                    .productThumbnailSnapshot(snap.getThumbnail())
                    .costPrice(snap.getCostPrice()) // Giá vốn
                    .warrantyExpireDate(warrantyDate) // Bảo hành
                    .build();
        }).collect(Collectors.toList());

        detailRepo.saveAll(details);
    }
}