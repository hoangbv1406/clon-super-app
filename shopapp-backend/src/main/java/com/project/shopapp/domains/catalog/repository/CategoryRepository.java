package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    boolean existsByParentIdAndNameAndIsDeleted(Integer parentId, String name, Long isDeleted);

    boolean existsBySlugAndIsDeleted(String slug, Long isDeleted);

    // Tối ưu hóa: Lấy TẤT CẢ category chưa bị xóa ra bằng 1 câu Query duy nhất
    // Sau đó Service sẽ tự dựng cây (Tree) trên RAM. Đánh bại hoàn toàn N+1.
    List<Category> findByIsDeletedOrderByLevelAscDisplayOrderAsc(Long isDeleted);

    // Lấy toàn bộ danh mục con của một danh mục (Sử dụng cột Path rất mạnh)
    @Query("SELECT c FROM Category c WHERE c.path LIKE concat(:parentPath, '%') AND c.isDeleted = 0")
    List<Category> findAllSubCategoriesByPath(String parentPath);
}