package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.VariantValue;
import com.project.shopapp.domains.catalog.entity.VariantValueId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantValueRepository extends JpaRepository<VariantValue, VariantValueId>, JpaSpecificationExecutor<VariantValue> {

    // Tìm cực nhanh các Variant ID dựa trên Option Value (Phục vụ Filter "Lọc màu Đỏ")
    @Query("SELECT v.variantId FROM VariantValue v WHERE v.productId = :productId AND v.optionValueId = :optionValueId")
    List<Integer> findVariantIdsByProductAndOptionValue(Integer productId, Integer optionValueId);

    // Xóa sạch dữ liệu mapping cũ khi update lại Variant
    @Modifying
    @Query("DELETE FROM VariantValue v WHERE v.variantId = :variantId")
    void deleteByVariantId(Integer variantId);
}