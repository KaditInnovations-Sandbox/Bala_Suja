package com.travelease.travelease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travelease.travelease.model.companymodel.company;

public interface CompanyRepository extends JpaRepository<company,Long>{
    @Query(nativeQuery = true, value ="SELECT * from company e WHERE e.companyid=:id")
    company checkById(@Param("id")Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM company e WHERE e.companyisactive = true")
    List<company> findByAccessTrue();

    @Query(nativeQuery = true, value = "SELECT * FROM company e WHERE e.companyisactive = false")
    List<company> findByAccessFalse();

    @Query(nativeQuery = true, value = "SELECT * FROM company WHERE companyname=:companyName")
    company findByComapnyName(@Param("companyName") String  companyName);

}
