package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.adminmodel.AdminRoleAssociation;
import com.travelease.travelease.model.companymodel.companyPassengerAssocaiation;

public interface CompanyPassengerRepository extends JpaRepository<companyPassengerAssocaiation,Long>{
    
    @Query(nativeQuery = true, value ="SELECT * from companyPassengerAssociation e WHERE e.id=:Admin")
    AdminRoleAssociation findByAdmin(@Param("Admin")Long Admin);
}
