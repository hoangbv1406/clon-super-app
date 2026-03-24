package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.api.OptionValueInternalApi;
import com.project.shopapp.domains.catalog.dto.request.ProductVariantCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductVariantResponse;
import com.project.shopapp.domains.catalog.entity.ProductVariant;
import com.project.shopapp.domains.catalog.mapper.ProductVariantMapper;
import com.project.shopapp.domains.catalog.repository.ProductRepository;
import com.project.shopapp.domains.catalog.repository.ProductVariantRepository;
import com.project.shopapp.domains.catalog.service.ProductVariantService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository variantRepo;
    private final ProductRepository productRepo;
    private final ProductVariantMapper variantMapper;
    private final OptionValueInternalApi optionValueApi; // Facade đã viết ở phần trước

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "product_variants", key = "#productId")
    public List<ProductVariantResponse> getVariantsOfProduct(Integer productId) {
        return variantRepo.findByProductIdAndIsDeleted(productId, 0L)
                .stream().map(variantMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "product_variants", key = "#request.productId")
    public ProductVariantResponse createVariant(Integer adminId, ProductVariantCreateRequest request) {
        if (!productRepo.existsById(request.getProductId())) {
            throw new DataNotFoundException("Sản phẩm gốc không tồn tại");
        }

        String sku = request.getSku();
        if (sku == null || sku.isBlank()) {
            sku = "SKU-" + request.getProductId() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } else if (variantRepo.existsBySkuAndIsDeleted(sku, 0L)) {
            throw new ConflictException("Mã SKU này đã tồn tại trong hệ thống");
        }

        ProductVariant variant = variantMapper.toEntityFromRequest(request);
        variant.setSku(sku);
        variant.setCreatedBy(adminId);

        // GỌI CROSS-API: Build JSON lưu vào cột attributes để sinh tên tự động
        Map<String, String> attributesJson = optionValueApi.generateAttributesMap(request.getOptionValueIds());
        // Thêm trường "name" vào JSON để MySQL tự extract ra cột ảo (Virtual Column)
        String generatedName = String.join(" - ", attributesJson.values());
        attributesJson.put("name", generatedName);

        variant.setAttributes(attributesJson);

        return variantMapper.toDto(variantRepo.save(variant));
    }
}