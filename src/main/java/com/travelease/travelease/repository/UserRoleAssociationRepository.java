package com.travelease.travelease.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.adminmodel.Role;
import com.travelease.travelease.model.adminmodel.UserRoleAssociation;


public interface UserRoleAssociationRepository extends JpaRepository<UserRoleAssociation,Long> {

    @Query(nativeQuery = true, value ="SELECT * from roleassociation e WHERE e.userid=:Admin")
    UserRoleAssociation findByAdmin(@Param("Admin")Long Admin);

    @Query(nativeQuery = true, value ="SELECT roleid from roleassociation e WHERE e.userid=:Admin")
    Role findRoleByAdmin(@Param("Admin")Long Admin);


} 