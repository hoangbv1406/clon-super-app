package com.project.shopapp.domains.catalog.api;
import com.project.shopapp.domains.catalog.dto.nested.ProductImageBasicDto;
import java.util.List;

public interface ProductImageInternalApi {
    List<ProductImageBasicDto> getGalleryByProductId(Integer productId);
}