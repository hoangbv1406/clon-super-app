package com.project.shopapp.domains.vendor.repository;

import com.project.shopapp.domains.vendor.entity.ShopEmployee;
import com.project.shopapp.domains.vendor.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopEmployeeRepository extends JpaRepository<ShopEmployee, Integer>, JpaSpecificationExecutor<ShopEmployee> {

    // Kiểm tra xem User này có đang làm việc ở Shop này không
    Optional<ShopEmployee> findByShopIdAndUserIdAndIsDeleted(Integer shopId, Integer userId, Long isDeleted);

    // Lấy danh sách nhân viên của 1 Shop
    List<ShopEmployee> findByShopIdAndIsDeleted(Integer shopId, Long isDeleted);

    boolean existsByShopIdAndUserIdAndStatusAndIsDeleted(Integer shopId, Integer userId, EmployeeStatus status, Long isDeleted);
}