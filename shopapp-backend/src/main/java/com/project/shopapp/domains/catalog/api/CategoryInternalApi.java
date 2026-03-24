package com.project.shopapp.domains.catalog.api;

import com.project.shopapp.domains.catalog.dto.nested.CategoryBasicDto;

public interface CategoryInternalApi {
    /**
     * Check xem Category này có tồn tại, đang Active, và không có danh mục con nào không (Leaf node)
     */
    boolean isValidLeafCategory(Integer categoryId);
    CategoryBasicDto getCategoryBasicInfo(Integer categoryId);
}