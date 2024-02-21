package com.travelease.travelease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.companymodel.company;


public interface CompanyRepository extends JpaRepository<company,Long>{
    @Query(nativeQuery = true, value ="SELECT * from company e WHERE e.company_email=:Email")
    company findByCompanyEmail(@Param("Email")String Email);
}
