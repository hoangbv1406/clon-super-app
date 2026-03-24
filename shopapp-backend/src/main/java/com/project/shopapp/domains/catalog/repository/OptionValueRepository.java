package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue, Integer>, JpaSpecificationExecutor<OptionValue> {

    boolean existsByOptionIdAndValueAndIsDeleted(Integer optionId, String value, Long isDeleted);

    // Tối ưu hóa: Lấy danh sách Values của 1 Option, sort chuẩn theo ý Admin
    List<OptionValue> findByOptionIdAndIsActiveTrueAndIsDeletedOrderByDisplayOrderAsc(Integer optionId, Long isDeleted);
}