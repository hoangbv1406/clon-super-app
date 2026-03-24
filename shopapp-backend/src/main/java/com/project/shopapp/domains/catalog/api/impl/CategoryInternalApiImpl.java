package com.project.shopapp.domains.catalog.api.impl;
import com.project.shopapp.domains.catalog.api.CategoryInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.CategoryBasicDto;
import com.project.shopapp.domains.catalog.entity.Category;
import com.project.shopapp.domains.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryInternalApiImpl implements CategoryInternalApi {
    private final CategoryRepository categoryRepo;

    @Override
    public boolean isValidLeafCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElse(null);
        if (category == null || !category.getIsActive() || category.getIsDeleted() != 0L) {
            return false;
        }
        // Leaf node là node không có con nào
        return category.getChildren() == null || category.getChildren().isEmpty();
    }

    @Override
    public CategoryBasicDto getCategoryBasicInfo(Integer categoryId) {
        // Tớ giả định cậu đang dùng CategoryRepository
        return categoryRepo.findById(categoryId)
                .map(category -> {
                    CategoryBasicDto dto = new CategoryBasicDto();
                    dto.setId(category.getId());
                    dto.setName(category.getName());
                    dto.setSlug(category.getSlug());
                    return dto;
                })
                .orElse(null); // Trả về null nếu không tìm thấy, bên kia (Marketing) sẽ tự handle
    }
}