package com.project.shopapp.domains.inventory.service.impl;

import com.project.shopapp.domains.inventory.dto.request.SupplierCreateRequest;
import com.project.shopapp.domains.inventory.dto.request.SupplierUpdateRequest;
import com.project.shopapp.domains.inventory.dto.response.SupplierResponse;
import com.project.shopapp.domains.inventory.entity.Supplier;
import com.project.shopapp.domains.inventory.enums.SupplierStatus;
import com.project.shopapp.domains.inventory.event.SupplierStatusChangedEvent;
import com.project.shopapp.domains.inventory.mapper.SupplierMapper;
import com.project.shopapp.domains.inventory.repository.SupplierRepository;
import com.project.shopapp.domains.inventory.service.SupplierService;
import com.project.shopapp.domains.inventory.specification.SupplierSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;
    private final SupplierMapper supplierMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SupplierResponse> searchSuppliers(Integer shopId, String keyword, String statusStr, int page, int size) {
        SupplierStatus status = null;
        if (StringUtils.hasText(statusStr)) {
            status = SupplierStatus.valueOf(statusStr.toUpperCase());
        }

        Page<Supplier> pagedResult = supplierRepo.findAll(
                SupplierSpecification.search(shopId, keyword, status),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(supplierMapper::toDto));
    }

    @Override
    @Transactional
    public SupplierResponse createSupplier(Integer currentUserId, Integer shopId, SupplierCreateRequest request) {
        if (StringUtils.hasText(request.getTaxCode()) &&
                supplierRepo.existsByShopIdAndTaxCodeAndIsDeleted(shopId, request.getTaxCode(), 0L)) {
            throw new ConflictException("Mã số thuế này đã tồn tại trong danh bạ của Shop.");
        }

        Supplier supplier = supplierMapper.toEntityFromRequest(request);
        supplier.setShopId(shopId);
        supplier.setCreatedBy(currentUserId);

        return supplierMapper.toDto(supplierRepo.save(supplier));
    }

    @Override
    @Transactional
    public SupplierResponse updateSupplier(Integer currentUserId, Integer shopId, Integer supplierId, SupplierUpdateRequest request) {
        Supplier supplier = supplierRepo.findByIdAndShopIdAndIsDeleted(supplierId, shopId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Nhà cung cấp không tồn tại trong hệ thống của bạn"));

        if (StringUtils.hasText(request.getTaxCode()) && !request.getTaxCode().equals(supplier.getTaxCode())) {
            if (supplierRepo.existsByShopIdAndTaxCodeAndIsDeleted(shopId, request.getTaxCode(), 0L)) {
                throw new ConflictException("Mã số thuế này đã bị trùng lặp.");
            }
            supplier.setTaxCode(request.getTaxCode());
        }

        if (request.getName() != null) supplier.setName(request.getName());
        if (request.getContactEmail() != null) supplier.setContactEmail(request.getContactEmail());
        if (request.getContactPhone() != null) supplier.setContactPhone(request.getContactPhone());
        if (request.getAddress() != null) supplier.setAddress(request.getAddress());

        SupplierStatus oldStatus = supplier.getStatus();
        if (request.getStatus() != null) {
            supplier.setStatus(SupplierStatus.valueOf(request.getStatus().toUpperCase()));
        }

        supplier.setUpdatedBy(currentUserId);
        Supplier saved = supplierRepo.save(supplier);

        // Bắn Event nếu thay đổi trạng thái hoạt động
        if (oldStatus != saved.getStatus()) {
            eventPublisher.publishEvent(new SupplierStatusChangedEvent(supplierId, shopId, saved.getStatus()));
        }

        return supplierMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteSupplier(Integer currentUserId, Integer shopId, Integer supplierId) {
        Supplier supplier = supplierRepo.findByIdAndShopIdAndIsDeleted(supplierId, shopId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Nhà cung cấp không tồn tại"));

        supplier.setIsDeleted(System.currentTimeMillis());
        supplier.setStatus(SupplierStatus.INACTIVE);
        supplier.setUpdatedBy(currentUserId);

        supplierRepo.save(supplier);
        eventPublisher.publishEvent(new SupplierStatusChangedEvent(supplierId, shopId, SupplierStatus.INACTIVE));
    }
}