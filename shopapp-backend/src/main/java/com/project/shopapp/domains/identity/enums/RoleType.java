package com.project.shopapp.domains.identity.enums;

public enum RoleType {
    USER("user"),
    ADMIN("admin"),
    VENDOR("vendor"),
    STAFF("staff");

    private final String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}