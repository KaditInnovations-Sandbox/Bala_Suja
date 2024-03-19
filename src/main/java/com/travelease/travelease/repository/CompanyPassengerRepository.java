package com.travelease.travelease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.companymodel.companyPassengerAssocaiation;

public interface CompanyPassengerRepository extends JpaRepository<companyPassengerAssocaiation,Long>{
    
    @Query(nativeQuery = true, value ="SELECT * from companypassengerassociation e WHERE e.companyid=:company")
    List<companyPassengerAssocaiation> findPassengerByCompany(@Param("company")Long company);
}
