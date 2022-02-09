package com.ivanxc.hse.dashboardrest.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    OWNER("OWNER"),
    VIEWER("VIEWER"),
    EDITOR("EDITOR");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
