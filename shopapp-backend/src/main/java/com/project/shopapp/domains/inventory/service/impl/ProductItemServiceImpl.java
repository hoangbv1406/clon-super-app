package com.project.shopapp.domains.inventory.service.impl;

import com.project.shopapp.domains.inventory.dto.request.ItemBatchCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.ProductItemResponse;
import com.project.shopapp.domains.inventory.entity.ProductItem;
import com.project.shopapp.domains.inventory.enums.ItemStatus;
import com.project.shopapp.domains.inventory.mapper.ProductItemMapper;
import com.project.shopapp.domains.inventory.repository.ProductItemRepository;
import com.project.shopapp.domains.inventory.service.ProductItemService;
import com.project.shopapp.domains.inventory.specification.ItemSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {

    private final ProductItemRepository itemRepository;
    private final ProductItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductItemResponse> searchItems(Integer productId, Integer variantId, String imei, String statusStr, int page, int size) {
        ItemStatus status = statusStr != null ? ItemStatus.valueOf(statusStr.toUpperCase()) : null;

        Page<ProductItem> pagedResult = itemRepository.findAll(
                ItemSpecification.filter(productId, variantId, imei, status),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(itemMapper::toDto));
    }

    @Override
    @Transactional
    public List<ProductItemResponse> batchCreateItems(Integer adminId, ItemBatchCreateRequest request) {
        List<ProductItem> itemsToSave = new ArrayList<>();

        for (String imei : request.getImeiCodes()) {
            if (itemRepository.findByImeiCodeAndIsDeleted(imei, 0L).isPresent()) {
                throw new ConflictException("IMEI " + imei + " đã tồn tại trong hệ thống!");
            }

            ProductItem item = ProductItem.builder()
                    .productId(request.getProductId())
                    .variantId(request.getVariantId())
                    .supplierId(request.getSupplierId())
                    .imeiCode(imei)
                    .inboundPrice(request.getInboundPrice())
                    .status(ItemStatus.AVAILABLE)
                    .build();
            item.setCreatedBy(adminId);
            itemsToSave.add(item);
        }

        // Lưu hàng loạt (Dựa vào HibernateBatchConfig đã cài ở file option_values đợt trước)
        List<ProductItem> savedItems = itemRepository.saveAll(itemsToSave);
        return savedItems.stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductItemResponse markAsDefective(Integer adminId, Integer itemId) {
        ProductItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new DataNotFoundException("Thiết bị không tồn tại"));

        if (item.getStatus() == ItemStatus.SOLD || item.getStatus() == ItemStatus.HOLD) {
            throw new ConflictException("Không thể đánh dấu hàng lỗi với thiết bị đang giao dịch hoặc đã bán.");
        }

        item.setStatus(ItemStatus.DEFECTIVE);
        item.setUpdatedBy(adminId);
        return itemMapper.toDto(itemRepository.save(item));
    }
}