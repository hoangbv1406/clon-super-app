package com.project.shopapp.domains.catalog.repository;

import com.project.shopapp.domains.catalog.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>, JpaSpecificationExecutor<Brand> {

    boolean existsBySlugAndIsDeleted(String slug, Long isDeleted);

    Optional<Brand> findBySlugAndIsDeleted(String slug, Long isDeleted);
}