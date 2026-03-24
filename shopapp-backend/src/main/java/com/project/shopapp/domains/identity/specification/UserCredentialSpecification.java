package com.project.shopapp.domains.identity.specification;

import com.project.shopapp.domains.identity.entity.UserCredential;
import com.project.shopapp.domains.identity.enums.AuthenticatorType;
import org.springframework.data.jpa.domain.Specification;

public class UserCredentialSpecification {
    public static Specification<UserCredential> belongsToUser(Integer userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("userId"), userId);
    }

    public static Specification<UserCredential> hasType(AuthenticatorType type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("authenticatorType"), type);
    }
}