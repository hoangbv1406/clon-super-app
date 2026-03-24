package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.api.BrandInternalApi;
import com.project.shopapp.domains.catalog.api.CategoryInternalApi;
import com.project.shopapp.domains.catalog.dto.request.ProductCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductResponse;
import com.project.shopapp.domains.catalog.entity.Product;
import com.project.shopapp.domains.catalog.mapper.ProductMapper;
import com.project.shopapp.domains.catalog.repository.ProductRepository;
import com.project.shopapp.domains.catalog.service.ProductService;
import com.project.shopapp.domains.catalog.specification.ProductSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import com.project.shopapp.shared.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ProductMapper productMapper;

    // Cross-module/Internal APIs
    private final CategoryInternalApi categoryApi;
    private final BrandInternalApi brandApi;
    // private final ShopInternalApi shopApi; (Giả lập gọi sang Vendor module)

    @Override
    @Transactional(readOnly = true)
    // List search không nên Cache Redis vì filter param quá lớn, nên dùng ElasticSearch ở tương lai.
    public PageResponse<ProductResponse> searchProducts(String keyword, Integer catId, Integer brandId, String vRam, int page, int size) {
        Page<Product> products = productRepo.findAll(
                ProductSpecification.searchAndFilter(keyword, catId, brandId, vRam),
                PageRequest.of(page - 1, size, Sort.by("totalSold").descending()) // Sort theo bán chạy nhất
        );
        return PageResponse.of(products.map(productMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "product_detail", key = "#slug") // Caching siêu quan trọng cho trang chi tiết SP
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepo.findBySlugAndIsDeleted(slug, 0L)
                .orElseThrow(() -> new DataNotFoundException("Sản phẩm không tồn tại"));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(Integer currentUserId, ProductCreateRequest request) {
        // NGHIỆP VỤ CHÉO 1: Phải chọn đúng Category mức Lá
        if (request.getCategoryId() != null && !categoryApi.isValidLeafCategory(request.getCategoryId())) {
            throw new InvalidParamException("Danh mục không hợp lệ hoặc không phải danh mục cấp cuối.");
        }
        // NGHIỆP VỤ CHÉO 2: Brand phải Active
        if (request.getBrandId() != null && !brandApi.isBrandActiveAndValid(request.getBrandId())) {
            throw new InvalidParamException("Thương hiệu bị khóa hoặc không tồn tại.");
        }

        // TODO: Check quyền Vendor của User hiện tại với request.getShopId()

        Product product = productMapper.toEntityFromRequest(request);

        String baseSlug = SlugUtils.toSlug(request.getName());
        String finalSlug = baseSlug;
        int counter = 1;
        while (productRepo.existsBySlugAndIsDeleted(finalSlug, 0L)) {
            finalSlug = baseSlug + "-" + counter++;
        }
        product.setSlug(finalSlug);
        product.setCreatedBy(currentUserId);

        return productMapper.toDto(productRepo.save(product));
    }
}