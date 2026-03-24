package com.project.shopapp.domains.catalog.api.impl;
import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.domains.catalog.repository.ProductRepository;
import com.project.shopapp.domains.catalog.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductInternalApiImpl implements ProductInternalApi {

    private final ProductRepository productRepo;
    private final ProductVariantRepository productVariantRepo;

    @Override
    @Transactional
    public boolean lockStock(Integer productId, Integer qty) {
        int updatedRows = productRepo.lockStock(productId, qty);
        return updatedRows > 0; // Nếu = 0 nghĩa là hết hàng hoặc Version thay đổi
    }

    @Override
    @Transactional
    public void unlockStock(Integer productId, Integer qty) {
        productRepo.unlockStock(productId, qty);
    }

    @Override
    @Transactional
    public void commitStock(Integer productId, Integer qty) {
        productRepo.commitStock(productId, qty);
    }

    @Override
    public BigDecimal getCurrentPrice(Integer productId, Integer variantId) {
        // Giả sử cậu dùng productVariantRepository để lấy giá
        // Nếu không tìm thấy, tớ trả về null để bên Marketing/Sales tự handle lỗi
        return productVariantRepo.findById(variantId)
                .map(variant -> variant.getPrice())
                .orElse(null);
    }

    @Override
    public ProductBasicDto getProductBasicInfo(Integer productId) {
        // Lấy thông tin cơ bản của sản phẩm (không kèm variant)
        return productRepo.findById(productId)
                .map(product -> {
                    ProductBasicDto dto = new ProductBasicDto();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    // dto.setThumbnail(product.getThumbnail()); // Nếu có field này
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public ProductBasicDto getProductBasicInfoForCart(Integer productId, Integer variantId) {
        return productVariantRepo.findById(variantId)
                .map(variant -> {
                    var product = variant.getProduct();

                    ProductBasicDto dto = new ProductBasicDto();
                    dto.setId(productId);
                    dto.setVariantId(variantId);
                    dto.setName(product.getName());

                    // SỬA Ở ĐÂY: Thay bằng getter đúng trong Entity ProductVariant của cậu
                    // Ví dụ nếu biến là 'name' thì dùng getName()
                    dto.setVariantName(variant.getName());

                    dto.setPrice(variant.getPrice());

                    // Ví dụ nếu biến là 'quantity' thì dùng getQuantity()
                    dto.setStockQuantity(variant.getQuantity());

                    dto.setThumbnail(variant.getImageUrl() != null ? variant.getImageUrl() : product.getThumbnail());

                    return dto;
                })
                .orElse(null);
    }
}