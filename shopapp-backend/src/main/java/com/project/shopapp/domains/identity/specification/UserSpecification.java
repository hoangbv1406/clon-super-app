package com.project.shopapp.domains.identity.specification;

import com.project.shopapp.domains.identity.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecification {
    public static Specification<User> filterUsers(String keyword, Integer roleId, Boolean isActive) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L); // Mặc định chỉ lấy user chưa xóa

            if (StringUtils.hasText(keyword)) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                var nameLike = cb.like(cb.lower(root.get("fullName")), likePattern);
                var emailLike = cb.like(cb.lower(root.get("email")), likePattern);
                var phoneLike = cb.like(root.get("phoneNumber"), likePattern);
                predicate = cb.and(predicate, cb.or(nameLike, emailLike, phoneLike));
            }
            if (roleId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("roleId"), roleId));
            }
            if (isActive != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isActive"), isActive));
            }
            return predicate;
        };
    }
}