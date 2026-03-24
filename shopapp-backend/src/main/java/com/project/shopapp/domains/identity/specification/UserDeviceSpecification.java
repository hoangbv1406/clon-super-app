package com.project.shopapp.domains.identity.specification;

import com.project.shopapp.domains.identity.entity.UserDevice;
import com.project.shopapp.domains.identity.enums.DeviceType;
import org.springframework.data.jpa.domain.Specification;

public class UserDeviceSpecification {
    public static Specification<UserDevice> activeDevicesOfType(DeviceType type) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isActive"), true);
            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get("deviceType"), type));
            }
            return predicate;
        };
    }
}