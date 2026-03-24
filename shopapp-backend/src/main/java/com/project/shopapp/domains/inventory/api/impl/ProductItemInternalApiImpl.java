package com.project.shopapp.domains.inventory.api.impl;

import com.project.shopapp.domains.inventory.api.ProductItemInternalApi;
import com.project.shopapp.domains.inventory.entity.ProductItem;
import com.project.shopapp.domains.inventory.event.ItemSoldEvent;
import com.project.shopapp.domains.inventory.repository.ProductItemRepository;
import com.project.shopapp.shared.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductItemInternalApiImpl implements ProductItemInternalApi {

    private final ProductItemRepository itemRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public List<Integer> lockAvailableItemsForOrder(Integer variantId, int quantity, Long orderId) {
        // Tìm N cái IMEI đang rảnh
        List<ProductItem> availableItems = itemRepo.findAvailableItemsForVariant(variantId, PageRequest.of(0, quantity));

        if (availableItems.size() < quantity) {
            throw new ConflictException("Kho vật lý không đủ thiết bị (Thiếu IMEI khả dụng).");
        }

        List<Integer> itemIdsToLock = availableItems.stream().map(ProductItem::getId).collect(Collectors.toList());
        LocalDateTime lockTime = LocalDateTime.now().plusMinutes(15); // Giữ trong 15 phút

        int lockedRows = itemRepo.lockItems(itemIdsToLock, orderId, lockTime);
        if (lockedRows != quantity) {
            throw new ConflictException("Hàng hóa vừa bị người khác mua. Vui lòng thử lại.");
        }

        return itemIdsToLock;
    }

    @Override
    @Transactional
    public void confirmSoldByOrder(Long orderId) {
        // 1. Lấy danh sách các Item (IMEI) đang bị khóa cho Order này
        List<ProductItem> items = itemRepo.findByOrderId(orderId);

        // 2. Chuyển trạng thái sang SOLD
        int updated = itemRepo.markAsSoldByOrder(orderId, LocalDateTime.now());

        // 3. Publish event ItemSoldEvent cho từng máy
        if (updated > 0 && items != null) {
            for (ProductItem item : items) {
                // FIX LỖI 2: Truyền thêm item.getVariantId() (hoặc getProductId() tùy theo entity của cậu) vào giữa
                eventPublisher.publishEvent(new ItemSoldEvent(item.getId(), item.getVariantId(), orderId));
            }
        }
    }

    // FIX LỖI 1: Bổ sung hàm giải phóng tồn kho cho Cron Job gọi
    @Override
    @Transactional
    public void releaseExpiredHoldItems() {
        LocalDateTime now = LocalDateTime.now();
        // Gọi xuống Repo để nhả (Unlock) các Item đã quá hạn giữ chỗ.
        int unlockedCount = itemRepo.releaseExpiredHolds(now);
        // Có thể thêm log ở đây nếu cần: log.info("Đã nhả {} items", unlockedCount);
    }
}
