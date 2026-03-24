package com.project.shopapp.domains.identity.repository;

import com.project.shopapp.domains.identity.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer>, JpaSpecificationExecutor<UserAddress> {

    // Lấy danh sách địa chỉ chưa bị xóa
    List<UserAddress> findByUserIdAndIsDeletedFalseOrderByIsDefaultDesc(Integer userId);

    // Cập nhật tất cả địa chỉ của user thành "không mặc định" (Transaction Atomic)
    @Modifying
    @Query("UPDATE UserAddress a SET a.isDefault = false WHERE a.userId = :userId")
    void resetDefaultAddress(Integer userId);

    long countByUserIdAndIsDeletedFalse(Integer userId);
}