package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.dto.request.CategoryCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.CategoryResponse;
import com.project.shopapp.domains.catalog.entity.Category;
import com.project.shopapp.domains.catalog.event.CategoryTreeChangedEvent;
import com.project.shopapp.domains.catalog.mapper.CategoryMapper;
import com.project.shopapp.domains.catalog.repository.CategoryRepository;
import com.project.shopapp.domains.catalog.service.CategoryService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import com.project.shopapp.shared.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "category_tree", key = "'public'") // Siêu quan trọng: Cache toàn bộ menu
    public List<CategoryResponse> getCategoryTree() {
        // 1 Query duy nhất lấy toàn bộ DB
        List<Category> allCategories = categoryRepository.findByIsDeletedOrderByLevelAscDisplayOrderAsc(0L);

        // Dựng cây trên RAM O(n)
        Map<Integer, CategoryResponse> dtoMap = new HashMap<>();
        List<CategoryResponse> rootNodes = new ArrayList<>();

        for (Category cat : allCategories) {
            if (!cat.getIsActive()) continue; // Frontend không cần hiển thị các mục bị ẩn

            CategoryResponse dto = categoryMapper.toDto(cat);
            dto.setChildren(new ArrayList<>());
            dtoMap.put(cat.getId(), dto);

            if (cat.getParentId() == null) {
                rootNodes.add(dto);
            } else {
                CategoryResponse parentDto = dtoMap.get(cat.getParentId());
                if (parentDto != null) {
                    parentDto.getChildren().add(dto);
                }
            }
        }
        return rootNodes;
    }

    @Override
    @Transactional
    @CacheEvict(value = "category_tree", allEntries = true) // Xóa Cache khi có thay đổi
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByParentIdAndNameAndIsDeleted(request.getParentId(), request.getName(), 0L)) {
            throw new ConflictException("Tên danh mục này đã tồn tại trong cùng cấp");
        }

        Category category = categoryMapper.toEntityFromRequest(request);
        String slug = SlugUtils.toSlug(request.getName());
        category.setSlug(slug);

        // Tính toán Level và Materialized Path
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new DataNotFoundException("Danh mục cha không tồn tại"));

            if (parent.getLevel() >= 3) { // Giới hạn độ sâu của menu (Enterprise Rule)
                throw new InvalidParamException("Hệ thống chỉ hỗ trợ danh mục tối đa 3 cấp");
            }
            category.setLevel(parent.getLevel() + 1);
            category.setPath(parent.getPath() + parent.getId() + "/");
        } else {
            category.setLevel(1);
            category.setPath("/");
        }

        Category saved = categoryRepository.save(category);
        eventPublisher.publishEvent(new CategoryTreeChangedEvent("CREATE", saved.getId()));

        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "category_tree", allEntries = true)
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow();

        // Lấy Path để check xem nó có con không
        String myPath = category.getPath() + category.getId() + "/";
        List<Category> children = categoryRepository.findAllSubCategoriesByPath(myPath);

        if (!children.isEmpty()) {
            throw new ConflictException("Không thể xóa danh mục đang chứa danh mục con. Hãy xóa con trước.");
        }
        // TODO: Cần check thêm xem có Product nào đang gắn vào Category này không trước khi xóa.

        category.setIsDeleted(System.currentTimeMillis());
        category.setIsActive(false);
        categoryRepository.save(category);

        eventPublisher.publishEvent(new CategoryTreeChangedEvent("DELETE", id));
    }
}