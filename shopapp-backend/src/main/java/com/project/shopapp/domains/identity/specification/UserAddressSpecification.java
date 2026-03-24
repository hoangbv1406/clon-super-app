package com.project.shopapp.domains.identity.specification;

import com.project.shopapp.domains.identity.entity.UserAddress;
import com.project.shopapp.domains.identity.enums.AddressType;
import org.springframework.data.jpa.domain.Specification;

public class UserAddressSpecification {
    public static Specification<UserAddress> hasType(AddressType type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("addressType"), type);
    }
}