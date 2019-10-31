package com.example.usermanagementservice.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return String.format("ROLE_%s", name());
    }

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
