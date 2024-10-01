package com.nexign.securityService.entity;


import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements Role, GrantedAuthority {
    USER, ADMIN;

    @Override
    public boolean includes(Role role) {
        return true;
    }


    @Override
    public String getAuthority() {
        return name();
    }
}
