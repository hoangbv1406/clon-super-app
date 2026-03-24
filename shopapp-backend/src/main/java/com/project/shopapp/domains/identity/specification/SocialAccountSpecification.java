package com.project.shopapp.domains.identity.specification;

import com.project.shopapp.domains.identity.entity.SocialAccount;
import com.project.shopapp.domains.identity.enums.SocialProvider;
import org.springframework.data.jpa.domain.Specification;

public class SocialAccountSpecification {
    public static Specification<SocialAccount> hasProvider(SocialProvider provider) {
        return (root, query, cb) -> provider == null ? null : cb.equal(root.get("provider"), provider);
    }
}