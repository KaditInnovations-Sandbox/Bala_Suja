package com.travelease.travelease.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.travelease.travelease.model.adminmodel.Role;
import com.travelease.travelease.repository.RoleRepository;

public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findByAdminRoleName(name);
    }
}
