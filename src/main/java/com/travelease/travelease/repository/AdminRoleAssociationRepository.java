package com.travelease.travelease.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.adminmodel.AdminRoleAssociation;


public interface AdminRoleAssociationRepository extends JpaRepository<AdminRoleAssociation,Long> {

    @Query(nativeQuery = true, value ="SELECT * from roleassociation e WHERE e.userid=:Admin")
    AdminRoleAssociation findByAdmin(@Param("Admin")Long Admin);
} 