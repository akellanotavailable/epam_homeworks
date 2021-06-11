package com.epam.expositions.entity;

import java.util.Set;

public enum Permission {
    USER("user"),
    CLIENT("client"),
    ADMIN("admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static Set<Permission> matchPermissions(Role role){
        switch (role){
            case USER:
                return Set.of(Permission.USER);
            case CLIENT:
                return Set.of(Permission.USER, Permission.CLIENT);
            case ADMIN:
                return Set.of(Permission.USER, Permission.CLIENT, Permission.ADMIN);
            default:
                return null;
        }
    }
}
