package com.travelease.travelease.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.adminmodel.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
    
    @Query(nativeQuery = true, value ="SELECT * FROM Role e WHERE e.admin_role_name = :AdminRoleName")
    Role findByAdminRoleName(@Param("AdminRoleName") String AdminRoleName);


}
