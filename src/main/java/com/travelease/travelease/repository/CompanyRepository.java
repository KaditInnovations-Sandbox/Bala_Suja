package com.travelease.travelease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.companymodel.company;


public interface CompanyRepository extends JpaRepository<company,Long>{
    @Query(nativeQuery = true, value ="SELECT * from company e WHERE e.company_email=:Email")
    company findByCompanyEmail(@Param("Email")String Email);

    @Query(nativeQuery = true, value = "SELECT * FROM company e WHERE e.companyactive = true")
    List<company> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM company e WHERE e.companyactive = false")
    List<company> findByAccessFalse();

    @Query(nativeQuery = true, value = "SELECT * FROM company WHERE companyname=:companyName")
    company findByComapnyName(@Param("companyName") String  companyName);
    

}
