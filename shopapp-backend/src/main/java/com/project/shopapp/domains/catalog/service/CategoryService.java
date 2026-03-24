package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.request.CategoryCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategoryTree();
    CategoryResponse createCategory(CategoryCreateRequest request);
    void deleteCategory(Integer id);
}