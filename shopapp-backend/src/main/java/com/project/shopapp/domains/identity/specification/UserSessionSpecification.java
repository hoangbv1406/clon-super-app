package com.project.shopapp.domains.identity.specification;

import com.project.shopapp.domains.identity.entity.UserSession;
import org.springframework.data.jpa.domain.Specification;

public class UserSessionSpecification {
    public static Specification<UserSession> hasIpAddress(String ipAddress) {
        return (root, query, cb) -> ipAddress == null ? null : cb.equal(root.get("ipAddress"), ipAddress);
    }
}