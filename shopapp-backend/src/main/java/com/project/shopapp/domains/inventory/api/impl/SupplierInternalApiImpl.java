package com.project.shopapp.domains.inventory.api.impl;
import com.project.shopapp.domains.inventory.api.SupplierInternalApi;
import com.project.shopapp.domains.inventory.dto.nested.SupplierBasicDto;
import com.project.shopapp.domains.inventory.enums.SupplierStatus;
import com.project.shopapp.domains.inventory.repository.SupplierRepository;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierInternalApiImpl implements SupplierInternalApi {

    private final SupplierRepository supplierRepo;

    @Override
    @Transactional(readOnly = true)
    public SupplierBasicDto getActiveSupplierForTransaction(Integer shopId, Integer supplierId) {
        return supplierRepo.findByIdAndShopIdAndIsDeleted(supplierId, shopId, 0L)
                .filter(s -> s.getStatus() == SupplierStatus.ACTIVE)
                .map(s -> {
                    SupplierBasicDto dto = new SupplierBasicDto();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    dto.setContactPhone(s.getContactPhone());
                    return dto;
                })
                .orElseThrow(() -> new DataNotFoundException("Nhà cung cấp không tồn tại hoặc đã bị khóa."));
    }
}