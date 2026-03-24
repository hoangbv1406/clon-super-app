package com.project.shopapp.domains.notification.specification;

import com.project.shopapp.domains.notification.entity.Notification;
import com.project.shopapp.domains.notification.enums.NotificationType;
import org.springframework.data.jpa.domain.Specification;

public class NotificationSpecification {
    public static Specification<Notification> filterForUser(Integer userId, Boolean isRead, NotificationType type) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("userId"), userId)
            );

            if (isRead != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isRead"), isRead));
            }
            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get("type"), type));
            }
            return predicate;
        };
    }
}