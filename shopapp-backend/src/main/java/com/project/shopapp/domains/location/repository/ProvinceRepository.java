package com.project.shopapp.domains.location.repository;

import com.project.shopapp.domains.location.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String>, JpaSpecificationExecutor<Province> {
    Optional<Province> findByCodeAndIsActiveTrue(String code);
}