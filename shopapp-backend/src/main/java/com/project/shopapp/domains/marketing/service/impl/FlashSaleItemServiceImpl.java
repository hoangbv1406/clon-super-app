// --- service/impl/FlashSaleItemServiceImpl.java ---
package com.project.shopapp.domains.marketing.service.impl;

import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.domains.marketing.dto.request.FlashSaleItemCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleItemResponse;
import com.project.shopapp.domains.marketing.entity.FlashSale;
import com.project.shopapp.domains.marketing.entity.FlashSaleItem;
import com.project.shopapp.domains.marketing.enums.FlashSaleStatus;
import com.project.shopapp.domains.marketing.mapper.FlashSaleItemMapper;
import com.project.shopapp.domains.marketing.repository.FlashSaleItemRepository;
import com.project.shopapp.domains.marketing.repository.FlashSaleRepository;
import com.project.shopapp.domains.marketing.service.FlashSaleItemService;
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
public class FlashSaleItemServiceImpl implements FlashSaleItemService {

    private final FlashSaleItemRepository itemRepo;
    private final FlashSaleRepository saleRepo;
    private final FlashSaleItemMapper itemMapper;
    private final ProductInternalApi productApi; // Gọi Facade Module Catalog

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FlashSaleItemResponse> getItemsByFlashSale(Integer flashSaleId, int page, int size) {
        Page<FlashSaleItem> pagedResult = itemRepo.findByFlashSaleIdAndIsDeleted(
                flashSaleId, 0L, PageRequest.of(page - 1, size, Sort.by("id").ascending())
        );

        return PageResponse.of(pagedResult.map(entity -> {
            FlashSaleItemResponse dto = itemMapper.toDto(entity);

            // Hydrate (làm đầy) dữ liệu từ Catalog (Tránh JOIN)
            ProductBasicDto productInfo = productApi.getProductBasicInfo(entity.getProductId());
            if (productInfo != null) {
                dto.setProductName(productInfo.getName());
                dto.setProductImage(productInfo.getThumbnail());
                dto.setOriginalPrice(productInfo.getPrice());
            }
            return dto;
        }));
    }

    @Override
    @Transactional
    public List<FlashSaleItemResponse> addItemsToFlashSale(Integer adminId, Integer flashSaleId, List<FlashSaleItemCreateRequest> requests) {
        FlashSale sale = saleRepo.findById(flashSaleId).orElseThrow(() -> new DataNotFoundException("Flash Sale không tồn tại"));

        if (sale.getStatus() != FlashSaleStatus.PENDING) {
            throw new ConflictException("Chỉ có thể thêm sản phẩm khi Flash Sale đang ở trạng thái PENDING");
        }

        List<FlashSaleItem> itemsToSave = new ArrayList<>();

        for (FlashSaleItemCreateRequest req : requests) {
            if (itemRepo.existsByFlashSaleIdAndProductIdAndVariantIdAndIsDeleted(flashSaleId, req.getProductId(), req.getVariantId(), 0L)) {
                throw new ConflictException("Sản phẩm ID " + req.getProductId() + " đã tồn tại trong đợt sale này.");
            }

            // TODO: Ở hệ thống thật, cần check xem `promotionalPrice` có đang CAO HƠN giá gốc của Sản phẩm hay không.

            FlashSaleItem item = itemMapper.toEntityFromRequest(req);
            item.setFlashSaleId(flashSaleId);
            item.setCreatedBy(adminId);
            itemsToSave.add(item);
        }

        return itemRepo.saveAll(itemsToSave).stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeItemFromFlashSale(Integer adminId, Long itemId) {
        FlashSaleItem item = itemRepo.findById(itemId).orElseThrow();
        if (item.getFlashSale().getStatus() != FlashSaleStatus.PENDING) {
            throw new ConflictException("Không thể xóa sản phẩm khỏi Flash Sale đang chạy hoặc đã kết thúc.");
        }

        item.setIsDeleted(System.currentTimeMillis());
        item.setUpdatedBy(adminId);
        itemRepo.save(item);
    }
}