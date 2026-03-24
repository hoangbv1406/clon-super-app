package com.project.shopapp.domains.inventory.repository;

import com.project.shopapp.domains.inventory.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {

    // Ngăn chặn 1 Shop tạo trùng 2 NCC có chung mã số thuế
    boolean existsByShopIdAndTaxCodeAndIsDeleted(Integer shopId, String taxCode, Long isDeleted);

    // Ngăn chặn lấy chéo dữ liệu: Phải query kèm shopId
    Optional<Supplier> findByIdAndShopIdAndIsDeleted(Integer id, Integer shopId, Long isDeleted);
}