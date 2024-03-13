package com.travelease.travelease.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.travelease.travelease.model.adminmodel.AdminRoleAssociation;


public class AdminInfoDetails implements UserDetails{

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public AdminInfoDetails(AdminRoleAssociation adminRoleAssociation) {
        name=adminRoleAssociation.getUser().getAdminName();
        password=adminRoleAssociation.getUser().getAdminPassword();
        authorities= Arrays.stream((adminRoleAssociation.getRole()).getAdminRoleName().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
