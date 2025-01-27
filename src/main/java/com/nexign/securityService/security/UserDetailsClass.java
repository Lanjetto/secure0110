package com.nexign.securityService.security;

import com.nexign.securityService.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class UserDetailsClass implements UserDetails {
    private final UserEntity userEntity;

    @Autowired
    public UserDetailsClass(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return userEntity.getPass();
    }

    @Override
    public String getUsername() {
        return userEntity.getLogin();
    }
}
