package com.project.shopapp.domains.location.specification;

import com.project.shopapp.domains.location.entity.District;
import com.project.shopapp.domains.location.enums.DistrictType;
import org.springframework.data.jpa.domain.Specification;

public class DistrictSpecification {
    public static Specification<District> hasProvinceCode(String provinceCode) {
        return (root, query, cb) -> provinceCode == null ? null : cb.equal(root.get("provinceCode"), provinceCode);
    }

    public static Specification<District> isOfType(DistrictType type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("type"), type);
    }
}