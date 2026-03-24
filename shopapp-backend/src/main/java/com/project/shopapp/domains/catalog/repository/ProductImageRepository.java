package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>, JpaSpecificationExecutor<ProductImage> {

    // Đếm số lượng ảnh để chống SPAM (VD: 1 SP chỉ được max 8 ảnh)
    long countByProductIdAndIsDeleted(Integer productId, Long isDeleted);

    // Lấy toàn bộ ảnh của 1 SP, sắp xếp theo DisplayOrder chuẩn UX
    List<ProductImage> findByProductIdAndIsDeletedOrderByDisplayOrderAsc(Integer productId, Long isDeleted);
}