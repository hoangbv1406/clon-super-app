package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer>, JpaSpecificationExecutor<Option> {

    boolean existsByCodeAndIsDeleted(String code, Long isDeleted);

    // Tối ưu: Lấy danh sách master data bằng 1 query, dùng cho cache
    List<Option> findByIsActiveTrueAndIsDeleted(Long isDeleted);
}