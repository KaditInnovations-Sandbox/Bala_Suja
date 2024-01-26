package com.travelease.travelease.cmdrunner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.travelease.travelease.model.adminmodel.Role;
import com.travelease.travelease.repository.RoleRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        if(isTableEmpty()){
            Role role1 = new Role();
            role1.setAdminRoleName("SUPER_ADMIN");
            role1.setAdminRoleDescription("CRUD");
            roleRepository.save(role1);
            Role role2 = new Role();
            role2.setAdminRoleName("TRIP_ADMIN");
            role2.setAdminRoleDescription("CRU");
            roleRepository.save(role2);   
        }
        
    }

    private boolean isTableEmpty() {
        Long rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Role", Long.class);
        return rowCount != null && rowCount == 0;
    }

}
