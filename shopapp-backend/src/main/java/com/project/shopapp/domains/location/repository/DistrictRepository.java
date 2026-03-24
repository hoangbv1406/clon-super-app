package com.project.shopapp.domains.location.repository;

import com.project.shopapp.domains.location.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, String>, JpaSpecificationExecutor<District> {
    // Chỉ query các huyện đang active của một tỉnh
    List<District> findByProvinceCodeAndIsActiveTrue(String provinceCode);

    Optional<District> findByCodeAndIsActiveTrue(String code);
}